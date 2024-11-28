package com.develop.saas.exception;

import com.develop.saas.dto.ErrorResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalCategoryExceptionHandler {

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCategoryAlreadyExists(CategoryAlreadyExistsException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), "CATEGORY_ALREADY_EXISTS");
    }

    @ExceptionHandler(CategoryPersistenceException.class)
    public ResponseEntity<ErrorResponse> handleCategoryPersistence(CategoryPersistenceException ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "CATEGORY_PERSISTENCE_ERROR");
    }

    @ExceptionHandler(CategoryProcessingException.class)
    public ResponseEntity<ErrorResponse> handleCategoryProcessing(CategoryProcessingException ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "CATEGORY_PROCESSING_ERROR");
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFound(CategoryNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "CATEGORY_NOT_FOUND");
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message, String errorCode) {
        ErrorResponse errorResponse =
                new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message, errorCode);
        return new ResponseEntity<>(errorResponse, status);
    }
}
