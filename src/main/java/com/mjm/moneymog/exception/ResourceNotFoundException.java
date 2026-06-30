package com.mjm.moneymog.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Object id) {
        super(resource + " with ID '" + id + "' was not found.");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
