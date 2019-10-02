package com.springboottest.carrental.customer.dao;

import com.springboottest.carrental.customer.entity.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomCustomerRepository {
    List<Customer> findByEmail(String email);
    List<Customer> findByDrivingLicence(String drivingLicence);
    List<Customer> findByLastName(String lastName);
}
