package com.defiapp.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EthereumAddressValidator implements ConstraintValidator<EthereumAddress, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EthereumAddressValidator.class);
    private static final String TOKEN_ADDRESS = "^0x[a-fA-F0-9]{40}$";
    private static final Pattern PATTERN = Pattern.compile(TOKEN_ADDRESS);

    @Override
    public void initialize(EthereumAddress constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null){
            LOGGER.error("The address cannot be null");
            return false;
        }
        return value.isEmpty() || PATTERN.matcher(value).matches();
    }
}