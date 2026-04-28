package com.example.studentapi.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("A student with email '" + email + "' is already registered.");
    }
}
