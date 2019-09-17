package com.springboottest.carrental.customer.jackson;

import com.springboottest.carrental.customer.entity.Customer;

public interface CustomerPojoToJson {
    void convertToJson(Customer customer);
}
