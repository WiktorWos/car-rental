package com.springboottest.carrental.validation;

import com.springboottest.carrental.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private CustomerService customerService;

    @Autowired
    public UniqueEmailValidator(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return (value!= null) && (!customerService.isEmailInUse(value));
    }
}
