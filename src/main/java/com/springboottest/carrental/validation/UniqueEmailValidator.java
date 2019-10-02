package com.springboottest.carrental.validation;

import com.springboottest.carrental.customer.entity.Customer;
import com.springboottest.carrental.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private CustomerService customerService;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        List<Customer> customers = customerService.isEmailInUse(value);
        if(isCustomerOnUpdate(customers)){
            return true;
        }
        return isEmailInUse(customers);
    }

    private boolean isCustomerOnUpdate(List<Customer> customers) {
        return !customers.isEmpty() && customers.get(0).getOnUpdate();
    }

    private boolean isEmailInUse(List<Customer> customers) {
        return customers.isEmpty();
    }
}
