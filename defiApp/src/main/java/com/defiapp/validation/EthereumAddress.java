package com.defiapp.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EthereumAddressValidator.class)
public @interface EthereumAddress {
    String message() default "Invalid address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}