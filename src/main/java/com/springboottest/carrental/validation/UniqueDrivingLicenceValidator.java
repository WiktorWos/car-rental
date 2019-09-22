package com.springboottest.carrental.validation;

import com.springboottest.carrental.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueDrivingLicenceValidator implements ConstraintValidator<UniqueDrivingLicence, String> {
    @Autowired
    private CustomerService customerService;

//    @Autowired
//    public UniqueDrivingLicenceValidator(CustomerService customerService) {
//        this.customerService = customerService;
//    }

    @Override
    public void initialize(UniqueDrivingLicence constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return (value != null) && (!customerService.isDrivingLicenceInUse(value));
    }
}
