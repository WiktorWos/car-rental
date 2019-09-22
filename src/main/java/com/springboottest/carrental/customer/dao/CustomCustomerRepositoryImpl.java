package com.springboottest.carrental.customer.dao;

import com.springboottest.carrental.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
public class CustomCustomerRepositoryImpl implements CustomCustomerRepository {
    private EntityManager entityManager;

    @Autowired
    public CustomCustomerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Customer findByEmail(String email) {
        Customer customer;

        try {
            customer = (Customer) entityManager.createQuery("FROM Customer c WHERE c.email = :email")
                    .setParameter("email",email).getSingleResult();
        } catch (NoResultException e) {
            customer = null;
        }

        return customer;
    }

    @Override
    public Customer findByDrivingLicence(String drivingLicence) {
        Customer customer;

        try {
            customer = (Customer) entityManager
                    .createQuery("FROM Customer c WHERE c.drivingLicenceNumber = :drivingLicence")
                    .setParameter("drivingLicence",drivingLicence).getSingleResult();
        } catch (NoResultException e) {
            customer = null;
        }

        return customer;
    }
}
