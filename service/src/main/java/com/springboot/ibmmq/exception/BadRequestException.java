package com.springboot.ibmmq.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String exMessage) {
        super(exMessage);
    }
}
