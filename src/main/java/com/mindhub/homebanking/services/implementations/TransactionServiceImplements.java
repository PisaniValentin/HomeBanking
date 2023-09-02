package com.mindhub.homebanking.services.implementations;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImplements implements TransactionService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void createTransaction(String fromAccountNumber, String toAccountNumber, float amount, String description,Account accountFrom, Account accountTo) {
        //create the transactions and update de balance of each account
        Transaction debitTransaction = new Transaction(-amount,description+" "+fromAccountNumber, TransactionType.DEBIT);
        Transaction creditTransaction = new Transaction(amount,description+" "+toAccountNumber,TransactionType.CREDIT);
        accountFrom.setBalance(accountFrom.getBalance()-amount);
        accountTo.setBalance(accountTo.getBalance()+amount);
        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
    }
}
