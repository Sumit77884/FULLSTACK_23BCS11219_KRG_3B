package com.classroom.jwt.config;

import com.classroom.jwt.model.User;
import com.classroom.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Same as Project 1 — pre-loads two users on startup.
// Users created:
//   username: user     password: user123   role: ROLE_USER
//   username: admin    password: admin123  role: ROLE_ADMIN
@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {

            userRepository.save(new User(
                    "user",
                    passwordEncoder.encode("user123"),
                    "ROLE_USER"
            ));

            userRepository.save(new User(
                    "admin",
                    passwordEncoder.encode("admin123"),
                    "ROLE_ADMIN"
            ));

            System.out.println("==============================================");
            System.out.println("  DEMO USERS CREATED:");
            System.out.println("  user  / user123  -> ROLE_USER");
            System.out.println("  admin / admin123 -> ROLE_ADMIN");
            System.out.println("  App running on http://localhost:8081");
            System.out.println("==============================================");
        }
    }
}
