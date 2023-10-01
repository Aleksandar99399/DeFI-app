package com.defiapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PropertyParamValidator.class)
public @interface PropertyParam {
    String message() default "Invalid property parameter";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
