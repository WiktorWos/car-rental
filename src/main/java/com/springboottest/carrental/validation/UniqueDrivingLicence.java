package com.springboottest.carrental.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueDrivingLicenceValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface UniqueDrivingLicence {
    public String message() default "There is already customer with this driving licence!";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default{};

}
