package com.defiapp.exceptions.customEx;

public class ExceededLimitPerUser extends GlobalCustomException{
    public ExceededLimitPerUser(String message) {
        super(message);
    }
}
