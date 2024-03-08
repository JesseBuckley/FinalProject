package com.skilldistillery.petconnectapp.exceptions;

public class CustomSecurityException extends RuntimeException {

    public CustomSecurityException() {
        super();
    }

    public CustomSecurityException(String message) {
        super(message);
    }

    public CustomSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomSecurityException(Throwable cause) {
        super(cause);
    }
}
