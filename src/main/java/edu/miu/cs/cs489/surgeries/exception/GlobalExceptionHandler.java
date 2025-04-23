package edu.miu.cs.cs489.surgeries.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                e.getMessage(),
                request.getRequestURI(),
                HttpStatus.NOT_FOUND.value(),
                Instant.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ApiError> handleDuplicateException(DuplicateException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                e.getMessage(),
                request.getRequestURI(),
                HttpStatus.CONFLICT.value(),
                Instant.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                e.getMessage(),
                request.getRequestURI(),
                HttpStatus.CONFLICT.value(),
                Instant.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                e.getMessage(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                Instant.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException e,
                                                                       HttpServletRequest request) {
        ApiError apiError = new ApiError(
                e.getMessage(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST.value(),
                Instant.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
