package com.springboottest.carrental.transaction.controller;

import com.springboottest.carrental.transaction.entity.Transaction;
import com.springboottest.carrental.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/findAll")
    public String findAll(Model model) {
        List<Transaction> transactions = transactionService.findAll();
        model.addAttribute("transactions",transactions);
        return "transaction-list";
    }
}
