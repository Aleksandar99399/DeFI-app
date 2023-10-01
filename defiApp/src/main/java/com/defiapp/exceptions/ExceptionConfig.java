package com.defiapp.exceptions;

import com.defiapp.exceptions.customEx.*;

public class ExceptionConfig {

    public static void throwError(String errorMessage) {
        if (errorMessage.contains("ExceededLimitPerUser")) {
            throw new ExceededLimitPerUser("The maximum tokens per user: 100");
        } else if (errorMessage.contains("ExceededLimitPerContract")) {
            throw new ExceededLimitPerContract("The maximum tokens per contract: 400");
        } else if (errorMessage.contains("UserAlreadyStakedAmount")) {
            throw new UserAlreadyStakedAmount("User already staked amount");
        } else if (errorMessage.contains("UserHasNotStakedAmount")) {
            throw new UserHasNotStakedAmount("User hasn't staked amount");
        } else if (errorMessage.contains("DeterminedPeriodIsNotExpired")) {
            throw new DeterminedPeriodNotExpired("Determined period isn't expired");
        } else if (errorMessage.contains("TokenDoesntExist")) {
            throw new TokenDoesntExist("Token doesn't exist");
        } else if (errorMessage.contains("NotStakedToken")) {
            throw new UserNotStakedToken("User didn't stake token");
        } else if (errorMessage.contains("Ownable")) {
            throw new OwnerException("The user is not owner");
        } else if (errorMessage.contains("TokenAlreadyExists")) {
            throw new TokenAlreadyExist("Token already exist");
        } else if (errorMessage.contains("insufficient funds")){
            throw new InsufficientFunds("Insufficient funds in the wallet");
        }
        else {
            throw new RuntimeException(errorMessage);
        }
    }

}
