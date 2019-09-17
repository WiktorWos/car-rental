package com.springboottest.carrental.transaction.service;

import com.springboottest.carrental.transaction.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> findAll();
    Transaction getById(Long id);
    void deleteById(Long id);
    void save(Transaction transaction);
}
