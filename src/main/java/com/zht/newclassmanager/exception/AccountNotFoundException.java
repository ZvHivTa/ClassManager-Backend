package com.zht.newclassmanager.exception;

import com.zht.newclassmanager.constant.MessageConstant;

public class AccountNotFoundException extends BaseException {
    public AccountNotFoundException() {
        super(MessageConstant.ACCOUNT_NOT_FOUND);
    }

    public AccountNotFoundException(String msg) {
        super(msg);
    }
}