package com.project.storage.handler;

public class WorkerNotFoundException extends RuntimeException {
    public WorkerNotFoundException(){
        super("Colaborador não encontrado!");
    }

    public WorkerNotFoundException(String msg, Object ... params){
        super(String.format(msg, params));
    }
}
