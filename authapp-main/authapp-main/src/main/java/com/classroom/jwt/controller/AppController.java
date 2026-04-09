package com.classroom.jwt.controller;

import com.classroom.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// ============================================================
// Protected endpoints — exactly the same as Project 1.
//
// The ONLY difference: in Project 1 the user was identified by
// a session cookie. In Project 2 they are identified by a JWT.
//
// But the controller code itself doesn't change at all.
// Security is handled transparently by the JwtAuthFilter.
// ============================================================
@RestController
public class AppController {

    @Autowired
    private UserRepository userRepository;

    // ----------------------------------------------------------
    // GET /dashboard
    // Requires: any valid JWT token
    // Header:   Authorization: Bearer <token>
    // ----------------------------------------------------------
    @GetMapping("/dashboard")
    public ResponseEntity<?> dashboard(
            @AuthenticationPrincipal UserDetails currentUser) {

        return ResponseEntity.ok(Map.of(
                "message", "Welcome to your dashboard!",
                "loggedInAs", currentUser.getUsername(),
                "yourRole", currentUser.getAuthorities().toString(),
                "note", "You reached here because your JWT token was valid."
        ));
    }

    // ----------------------------------------------------------
    // GET /admin/panel
    // Requires: JWT token with ROLE_ADMIN
    // Header:   Authorization: Bearer <token>
    // ----------------------------------------------------------
    @GetMapping("/admin/panel")
    public ResponseEntity<?> adminPanel(
            @AuthenticationPrincipal UserDetails currentUser) {

        return ResponseEntity.ok(Map.of(
                "message", "Admin panel — secret stuff here!",
                "admin", currentUser.getUsername()
        ));
    }

    // ----------------------------------------------------------
    // GET /users
    // Requires: any valid JWT token
    // ----------------------------------------------------------
    @GetMapping("/users")
    public ResponseEntity<?> listUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // ----------------------------------------------------------
    // GET /me
    // Returns the currently authenticated user's info.
    // Useful for frontends to know who they're logged in as.
    // ----------------------------------------------------------
    @GetMapping("/me")
    public ResponseEntity<?> me(
            @AuthenticationPrincipal UserDetails currentUser) {

        return ResponseEntity.ok(Map.of(
                "username", currentUser.getUsername(),
                "roles", currentUser.getAuthorities().toString(),
                "accountNonExpired", currentUser.isAccountNonExpired(),
                "accountNonLocked", currentUser.isAccountNonLocked()
        ));
    }
}
