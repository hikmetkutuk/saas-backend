package com.develop.saas.exception;

public class CategoryProcessingException extends RuntimeException {
    public CategoryProcessingException(String message) {
        super("Error processing category: " + message);
    }
}
