package com.springboot.ibmmq.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class BatchRequest {

    private List<Request> batchRequest;

    public List<Request> getBatchRequest() {
        return batchRequest;
    }

    public void setBatchRequest(List<Request> batchRequest) {
        this.batchRequest = batchRequest;
    }

    @Override
    public String toString() {
        return "BatchRequest{" +
            "batchRequest=" + batchRequest +
            '}';
    }
}
