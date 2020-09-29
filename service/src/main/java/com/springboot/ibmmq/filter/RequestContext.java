package com.springboot.ibmmq.filter;

public class RequestContext {

    private static final ThreadLocal<String> TRACKING_ID = new ThreadLocal<String>();

    public static String getTrackingId() {
        return TRACKING_ID.get();
    }

    public static void setTrackingId(String trackingId) {
        TRACKING_ID.set(trackingId);
    }

    public static void clear() {
        TRACKING_ID.remove();
    }
}

