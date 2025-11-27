package com.example.prime.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@ControllerAdvice
public class PrimeExceptionHandler {

    // allow for @Min / @NotNull validation errors
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(v -> formatViolationMessage(v))
                .collect(Collectors.joining("; "));
        return bad(message);
    }

    // build "param <annotation message>" using the annotation-provided message
    private String formatViolationMessage(ConstraintViolation<?> violation) {
        String path = violation.getPropertyPath().toString();
        String param = paramName(path);
        String msg = violation.getMessage();
        return String.format("%s %s", param, msg);
    }

    // return custom message for type mismatch errors
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String param = ex.getName();
        if ("limit".equals(param)) {
            return bad("must be an integer");
        }
        String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "expected type";
        String value = String.valueOf(ex.getValue());
        String message = String.format("Invalid value '%s' for parameter '%s' (expected %s)", value, param, requiredType);
        return bad(message);
    }

    // return custom message for missing request parameter
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParam(MissingServletRequestParameterException ex) {
        String param = ex.getParameterName();
        if ("limit".equals(param)) {
            return bad("cannot be null");
        }
        String message = "Missing value: " + param;
        return bad(message);
    }

    // map IllegalArgumentException from service-level validation to 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        String msg = ex.getMessage();
        return bad(msg != null ? msg : "invalid argument");
    }

    // map NullPointerException (e.g. autounboxing null) to a validation-style 400
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointer(NullPointerException ex) {
        // keep response consistent with missing/validation errors
        return bad("cannot be null");
    }

    // small helpers to reduce duplication
    private String paramName(String path) {
        if (path == null) return "";
        return path.contains(".") ? path.substring(path.lastIndexOf('.') + 1) : path;
    }

    private ResponseEntity<String> bad(String message) {
        return ResponseEntity.badRequest().body(message);
    }

    //return error message for maximnum limit exceeded

    //handle multiple values entered
}
