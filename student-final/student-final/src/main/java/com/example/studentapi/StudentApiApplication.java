package com.example.studentapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentApiApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("  Student API is running!");
        System.out.println("  POST: http://localhost:8080/api/students");
        System.out.println("  GET:  http://localhost:8080/api/students");
        System.out.println("  H2 DB: http://localhost:8080/h2-console");
        System.out.println("========================================\n");
    }
}
