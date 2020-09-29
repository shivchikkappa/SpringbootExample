package com.springboot.ibmmq.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BadRequestExceptionHandler extends ResponseEntityExceptionHandler {

    public BadRequestExceptionHandler() {
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {

        ServletWebRequest servletRequest = (ServletWebRequest)request;

        String path = servletRequest.getRequest().getRequestURI().toString();
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage() + ": " + error.getRejectedValue().toString()));

        ErrorResponse ce = new ErrorResponse(status, errors, path);
        return new ResponseEntity<Object>(ce, status);
    }
}
