package com.classroom.jwt.controller;

import com.classroom.jwt.model.User;
import com.classroom.jwt.repository.UserRepository;
import com.classroom.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// ============================================================
// *** NEW IN PROJECT 2 ***
//
// In Project 1, login was handled automatically by Spring's
// built-in form login filter.
//
// In Project 2, login is a normal REST endpoint.
// The client sends username + password as JSON.
// We verify them, then return a JWT token.
// The client must store and send that token on every future request.
// ============================================================
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ----------------------------------------------------------
    // POST /auth/login
    //
    // Request body:  { "username": "user", "password": "user123" }
    // Response:      { "token": "eyJhbGci..." }
    //
    // The client saves this token and sends it in every future request:
    //   Authorization: Bearer eyJhbGci...
    // ----------------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {

        String username = body.get("username");
        String password = body.get("password");

        try {
            // Step 1: Ask Spring Security to authenticate (checks DB via UserDetailsService)
            // This throws BadCredentialsException if wrong username/password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(401)
                    .body(Map.of("error", "Invalid username or password"));
        }

        // Step 2: Credentials are correct — generate a JWT token
        String token = jwtUtil.generateToken(username);

        // Step 3: Return the token to the client
        // The client must store this token (e.g. in localStorage or a variable)
        // and include it in the Authorization header on every future request
        return ResponseEntity.ok(Map.of(
                "token", token,
                "type", "Bearer",
                "username", username,
                "message", "Login successful! Use the token in the Authorization header."
        ));
    }

    // ----------------------------------------------------------
    // POST /auth/register
    //
    // Request body:  { "username": "alice", "password": "pass123" }
    // Response:      { "message": "...", "token": "eyJhbGci..." }
    //
    // Registers a new user AND returns a token so they can
    // immediately start using the API without logging in again.
    // ----------------------------------------------------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {

        String username = body.get("username");
        String password = body.get("password");

        // Check username is not already taken
        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Username already taken"));
        }

        // Save the new user with a hashed password
        userRepository.save(new User(
                username,
                passwordEncoder.encode(password),
                "ROLE_USER"
        ));

        // Give them a token immediately so they can start using the API
        String token = jwtUtil.generateToken(username);

        return ResponseEntity.ok(Map.of(
                "message", "Registered successfully!",
                "token", token,
                "type", "Bearer",
                "username", username
        ));
    }
}
