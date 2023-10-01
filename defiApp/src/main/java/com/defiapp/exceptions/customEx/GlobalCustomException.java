package com.defiapp.exceptions.customEx;

public abstract class GlobalCustomException extends RuntimeException{
    public GlobalCustomException(String message) {
        super(message);
    }
}
