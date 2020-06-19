package com.joseph.ipmanager.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IPAddressValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IPValue {
    String message() default "IP Address is Invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
