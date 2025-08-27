package com.project.storage.handler;

public class ApartmentUnavailableException extends RuntimeException {
    
    public ApartmentUnavailableException(){
        super("Apartamento indisponível para o período selecionado");
    }
}
