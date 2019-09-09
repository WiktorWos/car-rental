package com.springboottest.carrental.service;

import com.springboottest.carrental.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> findAll();
}
