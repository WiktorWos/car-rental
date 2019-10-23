package com.springboottest.carrental.transaction.controller;

import com.springboottest.carrental.car.entity.Car;
import com.springboottest.carrental.car.service.CarService;
import com.springboottest.carrental.customer.entity.Customer;
import com.springboottest.carrental.customer.service.CustomerService;
import com.springboottest.carrental.initbinder.StringTrimmer;
import com.springboottest.carrental.transaction.entity.Transaction;
import com.springboottest.carrental.transaction.savehandler.TransactionSaveHandler;
import com.springboottest.carrental.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    private TransactionService transactionService;
    private StringTrimmer stringTrimmer;
    private CarService carService;
    private CustomerService customerService;
    private TransactionSaveHandler transactionSaveHandler;

    @Autowired
    public TransactionController(TransactionService transactionService, StringTrimmer stringTrimmer,
                                 CarService carService, CustomerService customerService,
                                 TransactionSaveHandler transactionSaveHandler) {
        this.transactionService = transactionService;
        this.stringTrimmer = stringTrimmer;
        this.carService = carService;
        this.customerService = customerService;
        this.transactionSaveHandler = transactionSaveHandler;
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

        Transaction transactionWithCar = transactionSaveHandler.getTransactionWithCar(transaction);
        Transaction newOrUpdatedTransaction = transactionSaveHandler.getTransactionIfIsOnUpdate(transactionWithCar);
        Transaction transactionWithSetProperties = transactionSaveHandler.
                getTransactionWithSetProperties(newOrUpdatedTransaction);
        Transaction transactionReadyToSave = transactionSaveHandler.
                getTransactionWithCustomer(transactionWithSetProperties);

        transactionService.save(transactionReadyToSave);
        return "redirect:/transaction/findAll";
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
