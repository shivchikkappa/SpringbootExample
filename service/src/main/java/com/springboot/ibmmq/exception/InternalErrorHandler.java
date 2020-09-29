package com.springboot.ibmmq.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.UUID;

@ControllerAdvice
public class InternalErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(InternalErrorHandler.class);

    public InternalErrorHandler() {
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleGenericRuntimeException(RuntimeException ex, ServletWebRequest request) {
        String uuid = UUID.randomUUID().toString();
        String errMsg = "Error ID: " + uuid;
        logger.warn("Error " + uuid + ":" + ex.getMessage(), ex);

        String path = request.getRequest().getRequestURI().toString();

        // don't expose exception's message to user. Use errMsg which has an error id.
        // if customers report an error, use the error id to lookup logs
        ErrorResponse ce = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errMsg, path);
        return new ResponseEntity<ErrorResponse>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

