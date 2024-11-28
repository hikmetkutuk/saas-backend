package com.develop.saas.exception;

public class CategoryProcessingException extends RuntimeException {
    public CategoryProcessingException(String message, Throwable cause) {
        super("Error processing category: " + message, cause);
    }
}
