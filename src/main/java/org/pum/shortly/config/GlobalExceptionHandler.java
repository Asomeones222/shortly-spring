package org.pum.shortly.config;

import org.pum.shortly.exception.CodeNotFoundException;
import org.pum.shortly.exception.ShortURLGenerationFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CodeNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(CodeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ShortURLGenerationFailureException.class)
    public ResponseEntity<?> handleShortURLGenerationFailureException(ShortURLGenerationFailureException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
    }
}
