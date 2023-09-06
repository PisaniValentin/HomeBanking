package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping("/transactions")
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
            return new ResponseEntity<>("Origin account doesn't exist",HttpStatus.FORBIDDEN);
        }else{
            //check if the account belong to the client
            Client client = clientRepository.findByEmail(authentication.getName());
            if(accountFrom.getClient() != client){
                return new ResponseEntity<>("Account doesn't belong to the client",HttpStatus.FORBIDDEN);
            }
            //check if the accountTo exist
            if(accountTo == null){
                return new ResponseEntity<>("Destiny account doesn't exist",HttpStatus.FORBIDDEN);
            }
            //check if the account have the balance to do the transaction
            if(accountFrom.getBalance() < amount){
                return new ResponseEntity<>("Not enough balance to do the transaction",HttpStatus.FORBIDDEN);
            }else{
                transactionService.createTransaction(fromAccountNumber, toAccountNumber, amount,
                                                    description,accountFrom,accountTo);
                return new ResponseEntity<>("Transaction created",HttpStatus.CREATED);
            }
        }
    }

}
