package com.springboottest.carrental.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SearchedCustomerNotFoundValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface SearchedCustomerNotFound {
    public String message() default "Searched customer does not exist";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default{};
}
