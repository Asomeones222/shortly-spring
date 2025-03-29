package org.pum.shortly.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String shortURL) {
        super(String.format("Short code '%s' does not map to any valid URL.", shortURL));
    }
}
