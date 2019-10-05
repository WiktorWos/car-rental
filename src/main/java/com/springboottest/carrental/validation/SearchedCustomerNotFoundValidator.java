package com.springboottest.carrental.validation;

import com.springboottest.carrental.customer.entity.Customer;
import com.springboottest.carrental.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;


public class SearchedCustomerNotFoundValidator implements ConstraintValidator<SearchedCustomerNotFound, String> {
    @Autowired
    private CustomerService customerService;

    @Override
    public void initialize(SearchedCustomerNotFound constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        List<Customer> customersByLastName = customerService.searchCustomerByName(value);
        List<Customer> customersByDrivingLicence = customerService.isDrivingLicenceInUse(value);
        return isCustomerFound(customersByLastName, customersByDrivingLicence);
    }


    private boolean isCustomerFound(List<Customer> customersByLastName, List<Customer> customersByDrivingLicence) {
        return !customersByLastName.isEmpty() || !customersByDrivingLicence.isEmpty();
    }
}
