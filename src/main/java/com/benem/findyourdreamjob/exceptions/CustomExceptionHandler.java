package com.benem.findyourdreamjob.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidApiKeyException.class)
    public final ResponseEntity<Object> handleInvalidApiKeyException(InvalidApiKeyException ex, WebRequest request){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Api key validation failed", details);
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(FieldError error : ex.getFieldErrors()) {
            String errorMessage = error.getField().equals("email") ? "valós email címnek kell lennie" : error.getDefaultMessage();
            details.add(error.getField() + ": " + errorMessage);
        }

        ErrorResponse error = new ErrorResponse("Validation failed", details);
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> details = new ArrayList<>();

        for(ConstraintViolation error : ex.getConstraintViolations()) {
            details.add(error.getPropertyPath().toString().split("\\.")[1] + ": " + error.getMessage());
        }


        ErrorResponse error = new ErrorResponse("Validation failed", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
