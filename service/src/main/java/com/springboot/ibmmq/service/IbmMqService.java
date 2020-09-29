package com.springboot.ibmmq.service;

import com.springboot.ibmmq.dto.BatchRequest;
import com.springboot.ibmmq.dto.BatchResponse;

public interface IbmMqService {

    BatchResponse validateAndPostMessage(BatchRequest batchRequest);
}
