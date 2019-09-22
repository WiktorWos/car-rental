package com.springboottest.carrental.validation;

import com.springboottest.carrental.customer.entity.Customer;
import com.springboottest.carrental.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private CustomerService customerService;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Customer customer = customerService.isEmailInUse(value);
        if((customer != null) && customer.getOnUpdate()){
            return true;
        }
        return (value != null) && (customerService.isEmailInUse(value) == null);
    }
}
