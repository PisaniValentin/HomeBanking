package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Account;

public interface TransactionService {
    public void createTransaction(String fromAccountNumber, String toAccountNumber,
                                  float amount, String description,
                                  Account accountFrom,Account accountTo);
}
