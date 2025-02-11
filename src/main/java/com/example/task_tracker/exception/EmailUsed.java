package com.example.task_tracker.exception;

public class EmailUsed extends RuntimeException {
    public EmailUsed(String message) {
        super(message);
    }
}
