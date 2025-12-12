package com.zht.newclassmanager.exception;

import com.zht.newclassmanager.constant.MessageConstant;

public class PasswordErrorException extends BaseException {
    public PasswordErrorException() {
        super(MessageConstant.PASSWORD_ERROR);
    }
    public PasswordErrorException(String message) {
        super(message);
    }
}
