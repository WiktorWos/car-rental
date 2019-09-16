package com.springboottest.carrental.customer.service;

import com.springboottest.carrental.customer.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();
}
