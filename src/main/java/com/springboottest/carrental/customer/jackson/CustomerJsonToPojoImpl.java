package com.springboottest.carrental.customer.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboottest.carrental.customer.entity.Customer;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class CustomerJsonToPojoImpl implements CustomerJsonToPojo {
    @Override
    public Customer convertToPojo() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File("data/customerOutput.json"),Customer.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
