package com.afklm.usermanagement.exception;

public class UnauthorizedUserException extends IllegalArgumentException {
    public UnauthorizedUserException(String message) {
        super(message);
    }
}
