package com.springboot.ibmmq.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown=true)
public class BatchResponse  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "Success batch request list")
    private List<SuccessRequest> successBatchRequestList;

    @ApiModelProperty(notes = "Failed batch request list")
    private List<FailedRequest> failedBatchRequestList;

    public List<SuccessRequest> getSuccessBatchRequestList() {
        return successBatchRequestList;
    }

    public void setSuccessBatchRequestList(List<SuccessRequest> successBatchRequestList) {
        this.successBatchRequestList = successBatchRequestList;
    }

    public List<FailedRequest> getFailedBatchRequestList() {
        return failedBatchRequestList;
    }

    public void setFailedBatchRequestList(List<FailedRequest> failedBatchRequestList) {
        this.failedBatchRequestList = failedBatchRequestList;
    }

    @Override
    public String toString() {
        return "BatchResponse{" +
            "successBatchRequestList=" + successBatchRequestList +
            ", failedBatchRequestList=" + failedBatchRequestList +
            '}';
    }
}
