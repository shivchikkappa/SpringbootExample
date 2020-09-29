package com.springboot.ibmmq.dto;

import io.swagger.annotations.ApiModelProperty;

public class ViolationReason {

    @ApiModelProperty(notes = "Validation failed reason")
    private String reason;

    @ApiModelProperty(notes = "Validation failed value")
    private Object value;

    public ViolationReason() {
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ViolationReason{" +
            "reason='" + reason + '\'' +
            ", value=" + value +
            '}';
    }
}
