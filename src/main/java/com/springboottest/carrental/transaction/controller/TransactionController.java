package com.springboottest.carrental.transaction.controller;

import com.springboottest.carrental.car.entity.Car;
import com.springboottest.carrental.car.service.CarService;
import com.springboottest.carrental.customer.entity.Customer;
import com.springboottest.carrental.customer.jackson.CustomerJsonToPojo;
import com.springboottest.carrental.customer.service.CustomerService;
import com.springboottest.carrental.initbinder.StringTrimmer;
import com.springboottest.carrental.transaction.entity.Transaction;
import com.springboottest.carrental.transaction.price.PriceBase;
import com.springboottest.carrental.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    private TransactionService transactionService;
    private StringTrimmer stringTrimmer;
    private CarService carService;
    private CustomerJsonToPojo customerJsonToPojo;
    private PriceBase priceBase;
    private CustomerService customerService;

    @Autowired
    public TransactionController(TransactionService transactionService, StringTrimmer stringTrimmer,
                                 CarService carService, CustomerJsonToPojo customerJsonToPojo, PriceBase priceBase,
                                 CustomerService customerService) {
        this.transactionService = transactionService;
        this.stringTrimmer = stringTrimmer;
        this.carService = carService;
        this.customerJsonToPojo = customerJsonToPojo;
        this.priceBase = priceBase;
        this.customerService = customerService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        stringTrimmer.initBinder(webDataBinder);
    }

    @GetMapping("/findAll")
    public String findAll(Model model) {
        List<Transaction> transactions = transactionService.findAll();
        model.addAttribute("transactions",transactions);
        return "transaction-list";
    }

    @GetMapping("/addTransaction")
    public String addTransaction(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("availableCars", carService.findAvailableCars());
        return "transaction-form";
    }

    @PostMapping("/save")
    public String saveTransaction(@Valid @ModelAttribute Transaction transaction, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "transaction-form";
        }

        Transaction transactionWithCar = getTransactionWithCar(transaction);
        Transaction newOrUpdatedTransaction = getTransactionIfIsOnUpdate(transactionWithCar);
        Transaction transactionWithSetProperties = getTransactionWithSetProperties(newOrUpdatedTransaction);
        Transaction transactionReadyToSave = getTransactionWithCustomer(transactionWithSetProperties);

        transactionService.save(transactionReadyToSave);
        return "redirect:/transaction/findAll";
    }

    private Transaction getTransactionWithCar(Transaction transaction) {
        Long newCarId = getNewCarId(transaction);
        Car newCar = getNewCar(newCarId);
        transaction.setCar(newCar);
        return transaction;
    }

    private Transaction getTransactionIfIsOnUpdate(Transaction transaction) {
        if(isTransactionOnUpdate(transaction)) {
            Transaction updatedTransaction = getUpdatedTransaction(transaction);
            return updatedTransaction;
        }
        return transaction;
    }

    private boolean isTransactionOnUpdate(Transaction transaction) {
        return transaction.getId() != null;
    }

    private Long getNewCarId(Transaction transaction) {
        return transaction.getCar().getId();
    }

    private Transaction getUpdatedTransaction(Transaction transaction) {
        Long transactionId = transaction.getId();
        transaction = transactionService.getById(transactionId);
        return transaction;
    }

    private Car getNewCar(Long carId) {
        Car newCar = carService.getById(carId);
        return newCar;
    }

    private Transaction getTransactionWithSetProperties(Transaction transaction) {
        transaction.setStartDate(LocalDateTime.now());
        int mileageOfCarAssociatedWithTransaction = transaction.getCar().getCarMileage();
        transaction.setStartMileage(mileageOfCarAssociatedWithTransaction);
        Car carAssociatedWithTransaction = transaction.getCar();
        carAssociatedWithTransaction.setActive(true);
        return transaction;
    }

    private Transaction getTransactionWithCustomer(Transaction transaction) {
        if(isTransactionNew(transaction)) {
            Customer customer = getCustomerAssociatedWithTransaction();
            Customer existingOrNewCustomer = getCustomerIfExist(customer);
            transaction.setCustomer(existingOrNewCustomer);
            double priceBase = getPriceBase(transaction);
            transaction.setPrice(priceBase);
        }
        return transaction;
    }

    private boolean isTransactionNew(Transaction transaction) {
        return transaction.getCustomer() == null;
    }

    private Customer getCustomerAssociatedWithTransaction() {
        return customerJsonToPojo.convertToPojo();
    }

    private Customer getCustomerIfExist(Customer customer) {
        if(doesCustomerExist(customer)) {
            Long existingCustomerId = customer.getId();
            Customer existingCustomer = customerService.getById(existingCustomerId);
            return existingCustomer;
        }
        return customer;
    }

    private boolean doesCustomerExist(Customer customer) {
        return customer.getId() != null;
    }

    private double getPriceBase(Transaction transaction) {
        Customer customerAssociatedWithTransaction = transaction.getCustomer();
        int customerExperience = customerAssociatedWithTransaction.getExperience();
        double transactionPriceBase = priceBase.getPriceBase(customerExperience);
        return transactionPriceBase;
    }

    @GetMapping("/update")
    public String updateTransaction(@RequestParam Long id, Model model) {
        Transaction updatedTransaction = transactionService.getById(id);
        Car carAssociatedWithTransaction = updatedTransaction.getCar();
        carAssociatedWithTransaction.setActive(false);
        model.addAttribute("availableCars", carService.findAvailableCars());
        model.addAttribute("transaction", updatedTransaction);
        return "transaction-form";
    }


    @GetMapping("/delete")
    public String deleteTransaction(@RequestParam Long id) {
        Transaction deletedTransaction = transactionService.getById(id);
        Car carAssociatedWithTransaction = deletedTransaction.getCar();
        carAssociatedWithTransaction.setActive(false);

        if(doesCustomerHaveOnlyThisTransaction(deletedTransaction)) {
            deleteTransactionWithCustomer(deletedTransaction);
            return "redirect:/transaction/findAll";
        }

        deleteOnlyTransaction(deletedTransaction);
        return "redirect:/transaction/findAll";
    }

    private boolean doesCustomerHaveOnlyThisTransaction(Transaction transaction) {
        Customer customer = transaction.getCustomer();
        List<Transaction> customersTransaction = customer.getTransactions();
        int onlyOneTransaction = 1;
        return customersTransaction.size() == onlyOneTransaction;
    }

    private void deleteTransactionWithCustomer(Transaction transaction) {
        Customer customerAssociatedWithTransaction = transaction.getCustomer();
        Long customerId = customerAssociatedWithTransaction.getId();
        customerAssociatedWithTransaction.removeTransaction(transaction);
        customerService.deleteById(customerId);
        Long transactionId = transaction.getId();
        transactionService.deleteById(transactionId);
    }

    private void deleteOnlyTransaction(Transaction transaction) {
        Customer customerAssociatedWithTransaction = transaction.getCustomer();
        customerAssociatedWithTransaction.removeTransaction(transaction);
        Long transactionId = transaction.getId();
        transactionService.deleteById(transactionId);
    }
}
