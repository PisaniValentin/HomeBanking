package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @RequestMapping(path = "/transactions",method = RequestMethod.POST)
    public ResponseEntity<Object> createTransaction(@RequestParam String fromAccountNumber,@RequestParam String toAccountNumber,
                                                    @RequestParam float amount, @RequestParam String description,
                                                    Authentication authentication){
        if(description.isEmpty() || toAccountNumber.isEmpty() || fromAccountNumber.isEmpty() ){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Account accountFrom = accountRepository.findByNumber(fromAccountNumber);
        Account accountTo= accountRepository.findByNumber(toAccountNumber);
        //check the accountFrom exist check
        if(accountFrom == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }else{
            //check if the account belong to the client
            Client client = clientRepository.findByEmail(authentication.getName());
            if(accountFrom.getClient() != client){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            //check if the accountTo exist
            if(accountTo == null){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            //check if the account have the balance to do the transaction
            if(accountFrom.getBalance() < amount){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }else{
                //create the transactions and update de balance of each account
                Transaction debitTransaction = new Transaction(-amount,description+" "+fromAccountNumber,TransactionType.DEBIT);
                Transaction creditTransaction = new Transaction(amount,description+" "+toAccountNumber,TransactionType.CREDIT);
                accountFrom.setBalance(accountFrom.getBalance()-amount);
                accountTo.setBalance(accountTo.getBalance()+amount);
                transactionRepository.save(debitTransaction);
                transactionRepository.save(creditTransaction);
                accountRepository.save(accountFrom);
                accountRepository.save(accountTo);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }






    }

}
