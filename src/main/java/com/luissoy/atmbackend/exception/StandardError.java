package com.luissoy.atmbackend.exception;

public class StandardError {
    private final String message;
    private final String endpoint;

    public StandardError(String message, String endpoint) {
        this.message = message;
        this.endpoint = endpoint;
    }

    public String getMessage() {
        return message;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
