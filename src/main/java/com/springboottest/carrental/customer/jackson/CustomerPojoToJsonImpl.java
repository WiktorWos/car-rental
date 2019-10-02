package com.springboottest.carrental.customer.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springboottest.carrental.customer.entity.Customer;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class CustomerPojoToJsonImpl implements CustomerPojoToJson {
    public void convertToJson(Customer customer) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File("data/customerOutput.json"), customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
