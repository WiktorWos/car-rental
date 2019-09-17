package com.springboottest.carrental.transaction.controller;

import com.springboottest.carrental.car.service.CarService;
import com.springboottest.carrental.customer.entity.Customer;
import com.springboottest.carrental.customer.jackson.CustomerJsonToPojo;
import com.springboottest.carrental.initbinder.StringTrimmer;
import com.springboottest.carrental.transaction.entity.Transaction;
import com.springboottest.carrental.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    private TransactionService transactionService;
    private StringTrimmer stringTrimmer;
    private CarService carService;
    private CustomerJsonToPojo customerJsonToPojo;

    @Autowired
    public TransactionController(TransactionService transactionService, StringTrimmer stringTrimmer,
                                 CarService carService, CustomerJsonToPojo customerJsonToPojo) {
        this.transactionService = transactionService;
        this.stringTrimmer = stringTrimmer;
        this.carService = carService;
        this.customerJsonToPojo = customerJsonToPojo;
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
        transaction.setCar(carService.getById(transaction.getCar().getId()));
        transaction.setStartDate(LocalDate.now());
        transaction.setStartMileage(transaction.getCar().getCarMileage());
        transaction.setPrice(100);
        transaction.getCar().setActive(true);
        Customer customer = customerJsonToPojo.convertToPojo();
        customer.addTransaction(transaction);
        transaction.setCustomer(customer);
        transactionService.save(transaction);
        return "redirect:/transaction/findAll";
    }

    @GetMapping("/update")
    public String updateTransaction(@RequestParam Long id, Model model) {
        Transaction transaction = transactionService.getById(id);
        model.addAttribute("transaction", transaction);
        return "transaction-form";
    }

    @GetMapping("/delete")
    public String deleteTransaction(@RequestParam Long id) {
        transactionService.deleteById(id);
        return "redirect:/transaction/findAll";
    }
}
