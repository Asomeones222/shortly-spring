package org.pum.shortly.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String code) {
        super(String.format("Code '%s' does not map to any valid URL.", code));
    }
}
