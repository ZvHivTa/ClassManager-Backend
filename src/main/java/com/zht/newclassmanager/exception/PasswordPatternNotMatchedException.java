package com.zht.newclassmanager.exception;

import com.zht.newclassmanager.constant.MessageConstant;

public class PasswordPatternNotMatchedException extends BaseException {
    public PasswordPatternNotMatchedException() {
        super(MessageConstant.PASSWORD_PATTERN_NOT_MATCHED);
    }
    public PasswordPatternNotMatchedException(String message) {
        super(message);
    }
}
