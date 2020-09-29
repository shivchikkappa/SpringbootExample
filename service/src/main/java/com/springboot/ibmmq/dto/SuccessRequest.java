package com.springboot.ibmmq.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessRequest {

    @ApiModelProperty(notes = "Customer reference Id value from the request")
    private String customerRefId;

    public String getCustomerRefId() {
        return customerRefId;
    }

    public void setCustomerRefId(String customerRefId) {
        this.customerRefId = customerRefId;
    }

    @Override
    public String toString() {
        return "SuccessRequest{" +
            "customerRefId='" + customerRefId + '\'' +
            '}';
    }
}

