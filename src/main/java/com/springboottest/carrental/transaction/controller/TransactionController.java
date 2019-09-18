package com.springboottest.carrental.transaction.controller;

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
    private Transaction updateTransaction;
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

        if (transaction.getId() != null) {
            transaction.setCustomer(updateTransaction.getCustomer());
            transaction.setPrice(updateTransaction.getPrice());
        }

        transaction.setCar(carService.getById(transaction.getCar().getId()));
        transaction.setStartDate(LocalDateTime.now());
        transaction.setStartMileage(transaction.getCar().getCarMileage());

        transaction.getCar().setActive(true);

        if(transaction.getCustomer() == null) {
            Customer customer = customerJsonToPojo.convertToPojo();
            customer.addTransaction(transaction);
            transaction.setPrice(priceBase.getPriceBase(transaction.getCustomer().getExperience()));
        }


        transactionService.save(transaction);
        return "redirect:/transaction/findAll";
    }

    @GetMapping("/update")
    public String updateTransaction(@RequestParam Long id, Model model) {
        updateTransaction = transactionService.getById(id);
        updateTransaction.getCar().setActive(false);
        model.addAttribute("availableCars", carService.findAvailableCars());
        model.addAttribute("transaction", updateTransaction);
        return "transaction-form";
    }


    @GetMapping("/delete")
    public String deleteTransaction(@RequestParam Long id) {
        Transaction transaction = transactionService.getById(id);
        transaction.getCar().setActive(false);

        if(transaction.getCustomer().getTransactions().size() == 1) {
            Long customerId = transaction.getCustomer().getId();
            transaction.setCustomer(null);
            customerService.deleteById(customerId);
        }

        transactionService.deleteById(id);
        return "redirect:/transaction/findAll";
    }
}
