package com.springboot.ibmmq.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * A holder for one error message.  It is not named 'Error' because that is a standard java class name.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"timestamp", "status", "error", "message", "path"})
public class ErrorResponse {
    public static int DEFAULT_STATUS = 400;

    protected Date timestamp;
    protected int status;
    protected String error;
    protected Object message;
    protected String path;

    public ErrorResponse() {
        status = DEFAULT_STATUS;
    }

    public void setStatus(int status) {
        this.timestamp = new Date();
        this.status = status;
    }

    public ErrorResponse(int status, String error, Object message, String path) {
        this.timestamp = new Date();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ErrorResponse(HttpStatus httpStatus, Object message, String path) {
        this.timestamp = new Date();
        this.message = message;
        this.path = path;
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
    }

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.error = message;
    }

    @JsonProperty
    public int getStatus() {
        return status;
    }

    @JsonProperty
    public  Object getMessage() {
        return message;
    }

    @JsonProperty
    public String getError() {
        return error;
    }

    @JsonProperty
    public Date getTimestamp() {
        return timestamp;
    }

    @JsonProperty
    public String getPath() {
        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorResponse)) return false;

        ErrorResponse errorResponse = (ErrorResponse) o;

        if (status != errorResponse.status) return false;
        if (error != null ? !error.equals(errorResponse.error) : errorResponse.error != null) return false;
        if (message != null ? !message.equals(errorResponse.message) : errorResponse.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = status;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ErrorResponse [" +
            "status=" + status +
            ", error='" + error + '\'' +
            ", message='" + message + '\'' +
            ']';
    }
}

