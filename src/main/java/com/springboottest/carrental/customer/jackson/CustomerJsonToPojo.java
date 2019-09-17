package com.springboottest.carrental.customer.jackson;

import com.springboottest.carrental.customer.entity.Customer;

import java.io.File;

public interface CustomerJsonToPojo {
    Customer convertToPojo();
}
