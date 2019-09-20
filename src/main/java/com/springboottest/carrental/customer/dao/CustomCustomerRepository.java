package com.springboottest.carrental.customer.dao;

import com.springboottest.carrental.customer.entity.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomCustomerRepository {
    Customer findByEmail(String email);
    Customer findByDrivingLicence(String drivingLicence);
}
