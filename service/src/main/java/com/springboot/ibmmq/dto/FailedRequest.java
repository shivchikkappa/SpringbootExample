package com.springboot.ibmmq.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FailedRequest {

    @ApiModelProperty(notes = "Customer reference id value from the request")
    private String customerRefId;

    @ApiModelProperty(notes = "Validation reason failures list")
    private List<ViolationReason> reasons;

    @ApiModelProperty(hidden = true)
    private Request request;

    public FailedRequest() {
    }

    public String getCustomerRefId() {
        return customerRefId;
    }

    public void setCustomerRefId(String customerRefId) {
        this.customerRefId = customerRefId;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public List<ViolationReason> getReasons() {
        return reasons;
    }

    public void setReasons(List<ViolationReason> reasons) {
        this.reasons = reasons;
    }

    @Override
    public String toString() {
        return "FailedRequest{" +
            "customerRefId='" + customerRefId + '\'' +
            ", reasons=" + reasons +
            ", request=" + request +
            '}';
    }
}
