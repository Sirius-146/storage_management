package com.project.storage.handler;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String msg){
        super(msg);
    }

    public UnauthorizedException(String msg, Object ... params){
        super(String.format(msg, params));
    }
}
