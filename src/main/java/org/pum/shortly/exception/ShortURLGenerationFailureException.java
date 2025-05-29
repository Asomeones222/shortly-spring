package org.pum.shortly.exception;

public class ShortURLGenerationFailureException extends RuntimeException {
    public ShortURLGenerationFailureException() {
        super("Failed to generate short url. Please try again later.");
    }
}
