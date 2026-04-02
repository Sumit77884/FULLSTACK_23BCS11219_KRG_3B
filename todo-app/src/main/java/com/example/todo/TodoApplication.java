package com.example.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Todo CRUD Spring Boot application.
 *
 * Run with:
 *   mvn spring-boot:run
 *
 * Make sure MongoDB is running on localhost:27017
 */
@SpringBootApplication
public class TodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }
}
