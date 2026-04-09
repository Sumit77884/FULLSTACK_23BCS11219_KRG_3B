package com.classroom.jwt.config;

import com.classroom.jwt.filter.JwtAuthFilter;
import com.classroom.jwt.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// ============================================================
// *** COMPARE WITH PROJECT 1 ***
//
// Differences from Project 1:
//   1. sessionManagement → STATELESS  (no server-side sessions)
//   2. formLogin → REMOVED            (REST API, no HTML form)
//   3. csrf → DISABLED                (not needed for stateless APIs)
//   4. addFilterBefore               (our JwtAuthFilter added here)
//
// Everything else (URL rules, password encoder, auth provider)
// stays exactly the same.
// ============================================================
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;   // *** NEW ***

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // *** CHANGED: Disable CSRF — not needed for stateless REST APIs ***
            .csrf(csrf -> csrf.disable())

            // *** CHANGED: No sessions. Each request is independent. ***
            // The server stores NOTHING between requests.
            // The JWT token is the only proof of identity.
            .sessionManagement(sm -> sm
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // URL rules — same logic as Project 1
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/**", "/h2-console/**").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            )

            // *** CHANGED: No formLogin. JWT replaces the session cookie. ***
            // Login is now a normal POST /auth/login endpoint.

            // *** NEW: Register our JWT filter ***
            // It runs BEFORE Spring's built-in username/password filter.
            // So by the time Spring checks auth, we've already set the user.
            .addFilterBefore(jwtAuthFilter,
                    UsernamePasswordAuthenticationFilter.class)

            // H2 console fix
            .headers(headers -> headers
                    .frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}
