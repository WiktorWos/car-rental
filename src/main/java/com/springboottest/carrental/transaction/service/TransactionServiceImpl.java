package com.springboottest.carrental.transaction.service;

import com.springboottest.carrental.transaction.dao.TransactionRepository;
import com.springboottest.carrental.transaction.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    @Transactional
    public Transaction getById(Long id) {
        return transactionRepository.getOne(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
