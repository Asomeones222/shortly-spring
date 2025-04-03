package org.pum.shortly.exception;

public class CodeGenerationFailureException extends RuntimeException {
    public CodeGenerationFailureException() {
        super("Failed to generate code. Please try again later.");
    }
}
