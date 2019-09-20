package com.springboottest.carrental.customer.service;

import com.springboottest.carrental.customer.dao.CustomerRepository;
import com.springboottest.carrental.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public boolean isEmailInUse(String email) {
        boolean isEmailInUse = true;
        if(customerRepository.findByEmail(email) == null) {
            isEmailInUse = false;
        }
        return isEmailInUse;
    }

    @Override
    @Transactional
    public boolean isDrivingLicenceInUse(String email) {
        boolean isDrivingLicenceInUse = true;
        if(customerRepository.findByDrivingLicence(email) == null) {
            isDrivingLicenceInUse = false;
        }
        return isDrivingLicenceInUse;
    }
}
