package com.defiapp.validation;

import com.defiapp.model.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PropertyParamValidator implements ConstraintValidator<PropertyParam, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyParamValidator.class);

    @Override
    public void initialize(PropertyParam constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null){
            return true;
        }
        value = value.trim().replace(" ", "");
        for (Property property : Property.values()) {
            if (value.equalsIgnoreCase(property.name())){
                return true;
            }
        }
        LOGGER.error("Invalid property parameter: " + value);
        return false;
    }
}