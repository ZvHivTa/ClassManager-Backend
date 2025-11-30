package com.zht.newclassmanager.exception;

public class PasswordErrorException extends RuntimeException {
    public PasswordErrorException() {}
    public PasswordErrorException(String message) {
        super(message);
    }
}
