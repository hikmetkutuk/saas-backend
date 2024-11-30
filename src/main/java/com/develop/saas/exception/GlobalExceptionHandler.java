package com.develop.saas.exception;

import com.develop.saas.dto.ErrorResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleValidationException(MethodArgumentNotValidException ex) {
        List<ErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::mapToErrorResponse)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCategoryAlreadyExists(AlreadyExistsException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), "ALREADY_EXISTS");
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ErrorResponse> handleCategoryPersistence(PersistenceException ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "PERSISTENCE_ERROR");
    }

    @ExceptionHandler(ProcessingException.class)
    public ResponseEntity<ErrorResponse> handleCategoryProcessing(ProcessingException ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "PROCESSING_ERROR");
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFound(NotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "NOT_FOUND");
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message, String errorCode) {
        ErrorResponse errorResponse =
                new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message, errorCode);
        return new ResponseEntity<>(errorResponse, status);
    }

    private ErrorResponse mapToErrorResponse(FieldError fieldError) {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                fieldError.getDefaultMessage(),
                fieldError.getCode());
    }
}
