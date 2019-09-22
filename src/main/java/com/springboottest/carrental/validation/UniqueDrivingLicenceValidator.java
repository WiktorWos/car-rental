package com.springboottest.carrental.validation;

import com.springboottest.carrental.customer.entity.Customer;
import com.springboottest.carrental.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueDrivingLicenceValidator implements ConstraintValidator<UniqueDrivingLicence, String> {
    @Autowired
    private CustomerService customerService;

    @Override
    public void initialize(UniqueDrivingLicence constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Customer customer = customerService.isDrivingLicenceInUse(value);
        if((customer != null) && customer.getOnUpdate()){
            return true;
        }
        return (value != null) && (customerService.isDrivingLicenceInUse(value) == null);
    }
}
