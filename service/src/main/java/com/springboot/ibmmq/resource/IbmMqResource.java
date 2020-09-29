package com.springboot.ibmmq.resource;

import com.springboot.ibmmq.dto.BatchRequest;
import com.springboot.ibmmq.dto.BatchResponse;
import com.springboot.ibmmq.service.IbmMqService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@Api(value="API to validate and post message to IBM MQ")
public class IbmMqResource {

    private static final Logger log = LoggerFactory.getLogger(IbmMqResource.class);

    @Autowired
    private IbmMqService ibmMqService;

    @ApiOperation(value = "Batch Request", response = BatchResponse.class)
    @ApiResponses(value = {
        @ApiResponse(code = 202, message = "Accepted"),
        @ApiResponse(code = 207, message = "Multi-Status"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Server error")
    })
    @ResponseStatus(value = HttpStatus.ACCEPTED) //This is to hide the HTTP 200 response code in swagger API doc
    @PostMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BatchResponse> processBatchRequest(@Valid @RequestBody BatchRequest batchRequest) {

        BatchResponse response = ibmMqService.validateAndPostMessage(batchRequest);

        int totalRequestCount = batchRequest.getBatchRequest().size();
        int failedRequestCount = response.getFailedBatchRequestList().size();

        if (failedRequestCount == totalRequestCount) {
            log.error("batchResponse={}, responseCode={}",response, HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else if(failedRequestCount >= 1) {
            log.info("batchResponse={}, responseCode={}",response, HttpStatus.MULTI_STATUS.value());
            return new ResponseEntity<>(response, HttpStatus.MULTI_STATUS);
        } else {
            log.info("batchResponse={}, responseCode={}",response, HttpStatus.ACCEPTED.value());
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
    }

}
