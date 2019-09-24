package com.springboottest.carrental.customer.dao;

import com.springboottest.carrental.customer.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomCustomerRepository {
    Customer findByEmail(String email);
    Customer findByDrivingLicence(String drivingLicence);
    List<Customer> findByLastName(String lastName);
}
