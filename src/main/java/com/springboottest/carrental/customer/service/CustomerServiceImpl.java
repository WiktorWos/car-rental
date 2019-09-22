package com.springboottest.carrental.customer.service;

import com.springboottest.carrental.customer.dao.CustomerRepository;
import com.springboottest.carrental.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer getById(Long id) {
        return customerRepository.getOne(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Customer isEmailInUse(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null) {
            return null;
        }
        return customer;
    }

    @Override
    @Transactional
    public Customer isDrivingLicenceInUse(String drivingLicence) {
        Customer customer = customerRepository.findByDrivingLicence(drivingLicence);
        if(customer == null) {
            return null;
        }
        return customer;
    }
}
