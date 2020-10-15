package com.springboot.ibmmq.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    /**
     * Handle JSon deserialization error, such int, date deserializations.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex.getCause() instanceof JsonMappingException) {
            ServletWebRequest servletRequest = (ServletWebRequest)request;

            String path = servletRequest.getRequest().getRequestURI().toString();

            String msg = ex.getMessage();

            int index = msg.indexOf("; nested exception is");
            if (index > 0) {
                msg = msg.substring(0, index);
            }
            ErrorResponse ce = new ErrorResponse(status, msg, path);
            return new ResponseEntity<Object>(ce, status);
        } else  {
            // if the error is not JsonMappingException, keep the original handler
            return super.handleHttpMessageNotReadable(ex, headers, status, request);
        }
    }
}
