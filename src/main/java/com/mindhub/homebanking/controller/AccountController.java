package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }
    @RequestMapping("/accounts/{id}")
    public AccountDTO getClient(@PathVariable long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.map(AccountDTO::new).orElse(null);
    }

    @RequestMapping(path= "/api/clients/current/accounts",method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        if(client.getAccounts().size()>=3){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }else{
            String accountNumber = generateAccountNumber();
            boolean numberRepeated = false;
            while (!numberRepeated){
                if(accountRepository.findByNumber(accountNumber) != null){
                    accountNumber = generateAccountNumber();
                }else{
                    numberRepeated = true;
                }
            }
            Account account = new Account(accountNumber, LocalDate.now(),0);
            client.addAccount(account);
            account.setClient(client);
            accountRepository.save(account);
            clientRepository.save(client);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /**
     *
     * @return A valid and unique account number
     */
    private String generateAccountNumber(){
        Random random = new Random();
        int randomNumber = random.nextInt(100000000);
        String formattedNumber = String.format("%08d",randomNumber);
        return "VIN-"+formattedNumber;
    }

}
