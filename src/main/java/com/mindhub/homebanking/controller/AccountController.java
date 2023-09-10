package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAccounts();
    }
    @GetMapping("/accounts/{id}")
    public AccountDTO getClient(@PathVariable long id) {
        return accountService.getAccount(id);
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        return accountService.getClientAccounts(authentication);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        if(client.getAccounts().size()>=3){
            return new ResponseEntity<>("Client already have maximum account number",HttpStatus.FORBIDDEN);
        }
        accountService.createAccount(authentication);
        return new ResponseEntity<>("Account created",HttpStatus.CREATED);
    }
}
