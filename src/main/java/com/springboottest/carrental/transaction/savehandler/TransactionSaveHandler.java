package com.springboottest.carrental.transaction.savehandler;

import com.springboottest.carrental.car.entity.Car;
import com.springboottest.carrental.car.service.CarService;
import com.springboottest.carrental.customer.entity.Customer;
import com.springboottest.carrental.customer.jackson.CustomerJsonToPojo;
import com.springboottest.carrental.customer.service.CustomerService;
import com.springboottest.carrental.transaction.entity.Transaction;
import com.springboottest.carrental.transaction.price.PriceBase;
import com.springboottest.carrental.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionSaveHandler {
    private TransactionService transactionService;
    private CarService carService;
    private CustomerJsonToPojo customerJsonToPojo;
    private CustomerService customerService;
    private PriceBase priceBase;

    @Autowired
    public TransactionSaveHandler(TransactionService transactionService, CarService carService, CustomerJsonToPojo customerJsonToPojo, CustomerService customerService, PriceBase priceBase) {
        this.transactionService = transactionService;
        this.carService = carService;
        this.customerJsonToPojo = customerJsonToPojo;
        this.customerService = customerService;
        this.priceBase = priceBase;
    }

    public Transaction getTransactionWithCar(Transaction transaction) {
        Long newCarId = getNewCarId(transaction);
        Car newCar = getNewCar(newCarId);
        transaction.setCar(newCar);
        return transaction;
    }

    public Transaction getTransactionIfIsOnUpdate(Transaction transaction) {
        if(isTransactionOnUpdate(transaction)) {
            Transaction updatedTransaction = getUpdatedTransaction(transaction);
            return updatedTransaction;
        }
        return transaction;
    }

    public boolean isTransactionOnUpdate(Transaction transaction) {
        return transaction.getId() != null;
    }

    public Long getNewCarId(Transaction transaction) {
        return transaction.getCar().getId();
    }

    public Transaction getUpdatedTransaction(Transaction transaction) {
        Long transactionId = transaction.getId();
        transaction = transactionService.getById(transactionId);
        return transaction;
    }

    public Car getNewCar(Long carId) {
        Car newCar = carService.getById(carId);
        return newCar;
    }

    public Transaction getTransactionWithSetProperties(Transaction transaction) {
        transaction.setStartDate(LocalDateTime.now());
        int mileageOfCarAssociatedWithTransaction = transaction.getCar().getCarMileage();
        transaction.setStartMileage(mileageOfCarAssociatedWithTransaction);
        Car carAssociatedWithTransaction = transaction.getCar();
        carAssociatedWithTransaction.setActive(true);
        return transaction;
    }

    public Transaction getTransactionWithCustomer(Transaction transaction) {
        if(isTransactionNew(transaction)) {
            Customer customer = getCustomerAssociatedWithTransaction();
            Customer existingOrNewCustomer = getCustomerIfExist(customer);
            transaction.setCustomer(existingOrNewCustomer);
            double priceBase = getPriceBase(transaction);
            transaction.setPrice(priceBase);
        }
        return transaction;
    }

    public boolean isTransactionNew(Transaction transaction) {
        return transaction.getCustomer() == null;
    }

    public Customer getCustomerAssociatedWithTransaction() {
        return customerJsonToPojo.convertToPojo();
    }

    public Customer getCustomerIfExist(Customer customer) {
        if(doesCustomerExist(customer)) {
            Long existingCustomerId = customer.getId();
            Customer existingCustomer = customerService.getById(existingCustomerId);
            return existingCustomer;
        }
        return customer;
    }

    public boolean doesCustomerExist(Customer customer) {
        return customer.getId() != null;
    }

    public double getPriceBase(Transaction transaction) {
        Customer customerAssociatedWithTransaction = transaction.getCustomer();
        int customerExperience = customerAssociatedWithTransaction.getExperience();
        double transactionPriceBase = priceBase.getPriceBase(customerExperience);
        return transactionPriceBase;
    }
}
