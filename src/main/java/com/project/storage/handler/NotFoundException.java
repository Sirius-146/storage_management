package com.project.storage.handler;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg){
        super("%s not found!" + msg);
    }

    public NotFoundException(String msg, Object ... params){
        super(String.format(msg, params));
    }
}
