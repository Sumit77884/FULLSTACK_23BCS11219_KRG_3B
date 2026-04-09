package com.classroom.jwt.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

// ============================================================
// *** NEW IN PROJECT 2 ***
//
// JwtUtil handles all JWT operations:
//   1. generateToken  - create a token for a user after login
//   2. extractUsername - read who the token belongs to
//   3. isTokenValid   - check the token hasn't expired or been tampered with
//
// A JWT looks like: xxxxx.yyyyy.zzzzz
//   xxxxx = header  (algorithm used)
//   yyyyy = payload (username, expiry — readable by anyone!)
//   zzzzz = signature (only our server can produce this)
// ============================================================
@Component
public class JwtUtil {

    // Injected from application.properties
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    // Build the signing key from our secret string
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // ---- 1. Create a token ----
    // Called once after the user logs in successfully.
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)                              // Who this token is for
                .issuedAt(new Date())                          // When it was created
                .expiration(new Date(                          // When it expires
                        System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey())                     // Sign it with our secret
                .compact();                                    // Build the final string
    }

    // ---- 2. Read the username out of a token ----
    // Called on every protected request to know which user is calling.
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())   // Verify signature while parsing
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();                // getSubject() returns the username
    }

    // ---- 3. Check if the token is still valid ----
    // Returns true only if:
    //   - The username in the token matches the loaded user
    //   - The token has not expired yet
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            Date expiry = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();

            boolean usernameMatches = username.equals(userDetails.getUsername());
            boolean notExpired      = expiry.after(new Date());

            return usernameMatches && notExpired;

        } catch (Exception e) {
            // Any exception (bad signature, malformed token, expired) = invalid
            return false;
        }
    }
}
