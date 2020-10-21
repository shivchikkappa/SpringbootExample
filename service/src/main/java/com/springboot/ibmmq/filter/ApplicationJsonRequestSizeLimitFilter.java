package com.springboot.ibmmq.filter;

import com.springboot.ibmmq.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApplicationJsonRequestSizeLimitFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ApplicationJsonRequestSizeLimitFilter.class);

    @Value("${request.violation.payload.max.size}")
    private int requestPayloadSize;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException, BadRequestException {

        if (isApplicationJson(request) && request.getContentLengthLong() > requestPayloadSize) {
            log.error("Request content exceeded allowed limit "+ requestPayloadSize +" bytes. Request payload size="+request.getContentLengthLong());
            resolver.resolveException(request, response, null,
                new BadRequestException("Request content exceeded allowed limit "+ requestPayloadSize +" bytes. Request payload size="+request.getContentLengthLong()));
        } else {
            filterChain.doFilter(request, response);
        }

    }

    private boolean isApplicationJson(HttpServletRequest httpRequest) {
        return (MediaType.APPLICATION_JSON.isCompatibleWith(MediaType
            .parseMediaType(httpRequest.getHeader(HttpHeaders.CONTENT_TYPE))));
    }
}

