package com.springboottest.carrental.customer.dao;

import com.springboottest.carrental.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CustomCustomerRepositoryImpl implements CustomCustomerRepository {
    private EntityManager entityManager;

    @Autowired
    public CustomCustomerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Customer> findByEmail(String email) {
        List <Customer> customers;

        customers = entityManager.createQuery("FROM Customer c WHERE c.email = :email", Customer.class)
                    .setParameter("email",email).getResultList();

        return customers;
    }

    @Override
    public List<Customer> findByDrivingLicence(String drivingLicence) {
        List <Customer> customers;

        customers = entityManager
                    .createQuery("FROM Customer c WHERE c.drivingLicenceNumber = :drivingLicence",Customer.class)
                    .setParameter("drivingLicence",drivingLicence).getResultList();

        return customers;
    }

    @Override
    public List<Customer> findByLastName(String lastName) {
        List<Customer> customers;

        customers = entityManager
                    .createQuery("FROM Customer c WHERE c.lastName = :lastName", Customer.class)
                    .setParameter("lastName",lastName).getResultList();

        return customers;
    }
}
