package com.springboot.ibmmq.filter;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class IncomingRequestFilter implements Filter {

    public static final String TRACKING_ID = "trackingId";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        /* unused */
    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        //Add Tracking-Id for downstream logging
        String trackingId = httpRequest.getHeader(TRACKING_ID);
        if (StringUtils.isEmpty(trackingId)) {
            trackingId = generateTrackingId();
        }
        addMDC(TRACKING_ID, trackingId);
        RequestContext.setTrackingId(trackingId);

        // Include the Tracking-Id in the response
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.addHeader(TRACKING_ID, trackingId);

        chain.doFilter(request, response);
    }

    private void addMDC(final String key, final String value) {
        if (StringUtils.isEmpty(value)) {
            // If no value is set explicitly remove it to avoid a previous request value
            // from being associated with the current request.
            MDC.remove(key);
        } else {
            MDC.put(key, value);
        }
    }

    private String generateTrackingId() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}

