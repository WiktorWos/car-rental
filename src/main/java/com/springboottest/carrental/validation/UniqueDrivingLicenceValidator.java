package com.springboottest.carrental.validation;

import com.springboottest.carrental.customer.entity.Customer;
import com.springboottest.carrental.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class UniqueDrivingLicenceValidator implements ConstraintValidator<UniqueDrivingLicence, String> {
    @Autowired
    private CustomerService customerService;

    @Override
    public void initialize(UniqueDrivingLicence constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        List<Customer> customers = customerService.isDrivingLicenceInUse(value);
        if(isCustomerOnUpdate(customers)){
            return true;
        }
        return isDrivingLicenceNotUsed(customers);
    }

    private boolean isCustomerOnUpdate(List<Customer> customers) {
        return !customers.isEmpty() && customers.get(0).getOnUpdate();
    }

    private boolean isDrivingLicenceNotUsed(List<Customer> customers) {
        return customers.isEmpty();
    }
}
