package com.example.telegramapteka.exceptions;

import javax.naming.AuthenticationException;

public class RequireTelegramMfaException extends AuthenticationException {

    public RequireTelegramMfaException(String msg){
        super(msg);
    }
}
