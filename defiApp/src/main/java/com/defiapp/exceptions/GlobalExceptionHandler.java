package com.defiapp.exceptions;


import com.defiapp.exceptions.customEx.GlobalCustomException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(GlobalCustomException.class)
    public ResponseEntity<String> handleGlobalCustomException(GlobalCustomException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleConstraintExceptions(ConstraintViolationException ex) {
        List<String> errorMessage = new ArrayList<>();
        for (ConstraintViolation<?> constEx : ex.getConstraintViolations()) {
            errorMessage.add(constEx.getMessage());
        }
        return ResponseEntity.badRequest().body(errorMessage);

    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  @NotNull HttpStatus status, WebRequest request) {
        Map<String, String> errorMessages = new HashMap<>();
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        for (ObjectError error : errors) {
            String fieldOrObject = "";
            if (error instanceof FieldError) {
                fieldOrObject = ((FieldError) error).getField();
            } else if (error instanceof ObjectError) {
                fieldOrObject = error.getObjectName();
            }
            String errorMessage = error.getDefaultMessage();
            errorMessages.put(fieldOrObject, errorMessage);
        }
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }
}