package com.develop.saas.exception;

public class CategoryPersistenceException extends RuntimeException {
    public CategoryPersistenceException(String message, Throwable cause) {
        super("Error saving category: " + message, cause);
    }
}
