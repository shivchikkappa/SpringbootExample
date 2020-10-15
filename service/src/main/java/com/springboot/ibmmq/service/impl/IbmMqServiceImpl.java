package com.springboot.ibmmq.service.impl;

import com.google.common.base.Stopwatch;
import com.springboot.ibmmq.dto.BatchRequest;
import com.springboot.ibmmq.dto.BatchResponse;
import com.springboot.ibmmq.dto.FailedRequest;
import com.springboot.ibmmq.dto.Request;
import com.springboot.ibmmq.dto.SuccessRequest;
import com.springboot.ibmmq.dto.ViolationReason;
import com.springboot.ibmmq.filter.RequestContext;
import com.springboot.ibmmq.service.IbmMqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service("IbmMqService")
@Transactional
public class IbmMqServiceImpl implements IbmMqService {

    private static final Logger log = LoggerFactory.getLogger(IbmMqServiceImpl.class);

    @Value("${request.violation.response.outputRequest:false}")
    private boolean outputRequest = false;

    @Value("${ibm.mq.queue}")
    private String queueName;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Validator validator;

    private void validateBatch (List<Request> requestList, List<FailedRequest> failedReqList, List<SuccessRequest> successReqList, List<Request> validReqList) {
        for (Request r : requestList) {
            Set<ConstraintViolation<Request>> violations = validator.validate(r);
            if (!violations.isEmpty()) {
                List<ViolationReason> reasons = new ArrayList<ViolationReason>();
                for (ConstraintViolation<Request> v : violations) {
                    ViolationReason reason = new ViolationReason();
                    reason.setReason(v.getMessage());
                    reason.setValue(v.getInvalidValue());
                    reasons.add(reason);
                }
                FailedRequest fr = new FailedRequest();
                fr.setCustomerRefId(String.valueOf(r.getCustomerRefId()));
                if (outputRequest)
                    fr.setRequest(r);
                fr.setReasons(reasons);
                failedReqList.add(fr);
            } else {
                SuccessRequest sr  = new SuccessRequest();
                sr.setCustomerRefId(r.getCustomerRefId());
                successReqList.add(sr);
                validReqList.add(r);
            }
        }
    }

    @Override
    public BatchResponse validateAndPostMessage(BatchRequest batchRequest) {

        Stopwatch timer = Stopwatch.createStarted();

        BatchResponse batchResponse = new BatchResponse();

        try {
            List<Request> requestList = batchRequest.getBatchRequest();
            List<FailedRequest> failedReqList = new ArrayList<>();
            List<SuccessRequest> successReqList = new ArrayList<>();
            List<Request> validReqList = new ArrayList<>();

            validateBatch(requestList, failedReqList, successReqList, validReqList);
            batchResponse.setFailedBatchRequestList(failedReqList);
            batchResponse.setSuccessBatchRequestList(successReqList);

            //Post the message to queue
            String hostName = getHostName();
            jmsTemplate.setSessionTransacted(true);
            if (!validReqList.isEmpty()) {
                for (Request req : validReqList) {
                    req.setUniqueId(RequestContext.getTrackingId());
                    req.setHost(hostName);
                    jmsTemplate.convertAndSend(queueName,  req);
                    log.debug("Request posted to queue={}, trackingId={}, hostname={}, request={}",
                        queueName,RequestContext.getTrackingId(),hostName,req);
                }
            }

            return batchResponse;
        } finally {
            log.info("Time taken to processBatchRequest, queue={}, request={}, time={}", queueName, batchRequest, timer.elapsed(TimeUnit.MILLISECONDS));
        }
    }

    private String getHostName() {
        String hostName = "unknown";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException uhe) {
            log.error("UnknownHostException while getting hostname");
        }
        return hostName;
    }

    //Method to count and print all the message from IBM MQ. Should be used only for dev testing with very few messages in the queue.
    /*private void printMessage(JmsTemplate jmsTemplate) {
        int count = this.jmsTemplate.browse("DEV.QUEUE.1", new BrowserCallback<Integer>() {
            public Integer doInJms(final Session session, final QueueBrowser browser) throws JMSException {
                Enumeration enumeration = browser.getEnumeration();
                int counter = 0;
                while (enumeration.hasMoreElements()) {
                    TextMessage msg = (TextMessage) enumeration.nextElement();
                    log.info("msg.getText={}",msg.getText());
                    counter += 1;
                }
                return counter;
            }
        });
        log.info("count={}",count);
    }*/
}
