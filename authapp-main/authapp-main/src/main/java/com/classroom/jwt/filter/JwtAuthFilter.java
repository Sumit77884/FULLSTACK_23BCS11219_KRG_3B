package com.classroom.jwt.filter;

import com.classroom.jwt.service.CustomUserDetailsService;
import com.classroom.jwt.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// ============================================================
// *** NEW IN PROJECT 2 ***
//
// This filter runs on EVERY incoming request — before it
// reaches your controller.
//
// It does one job:
//   "If the request has a valid JWT token in the header,
//    tell Spring Security who this user is."
//
// Without this filter, Spring Security doesn't know that
// a JWT token means the user is authenticated.
//
// Flow:
//   Request comes in
//     → Does it have "Authorization: Bearer <token>"?
//       NO  → skip, let the filter chain handle it (will get 401)
//       YES → extract token
//           → validate it
//           → if valid, set user into SecurityContext
//           → continue to controller
// ============================================================
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Step 1: Read the Authorization header
        String authHeader = request.getHeader("Authorization");

        // Step 2: If there's no header or it doesn't start with "Bearer ", skip
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Step 3: Pull out the token (remove the "Bearer " prefix)
        String token = authHeader.substring(7);

        // Step 4: Extract the username from the token
        String username = null;
        try {
            username = jwtUtil.extractUsername(token);
        } catch (Exception e) {
            // Token is malformed or has bad signature — just skip
            filterChain.doFilter(request, response);
            return;
        }

        // Step 5: If we got a username AND nobody is already authenticated
        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load the full user from the database
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Step 6: Validate the token
            if (jwtUtil.isTokenValid(token, userDetails)) {

                // Step 7: Create an authentication object and put it in the context
                // This is how Spring Security knows the user is authenticated
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,                          // No credentials needed (already verified)
                                userDetails.getAuthorities()   // Their roles
                        );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                // THIS is the key line — it tells Spring Security:
                // "Trust this user for the rest of this request"
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Step 8: Pass the request along to the next filter / controller
        filterChain.doFilter(request, response);
    }
}
