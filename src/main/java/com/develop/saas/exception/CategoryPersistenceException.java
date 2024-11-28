package com.develop.saas.exception;

public class CategoryPersistenceException extends RuntimeException {
    public CategoryPersistenceException(String message) {
        super("Error saving category: " + message);
    }
}
