package org.pum.shortly.exception;

public class CodeNotFoundException extends RuntimeException {
    public CodeNotFoundException(String code) {
        super(String.format("Code '%s' does not map to any valid URL.", code));
    }
}
