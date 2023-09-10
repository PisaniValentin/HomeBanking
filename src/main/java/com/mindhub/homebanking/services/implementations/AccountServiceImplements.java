package com.mindhub.homebanking.services.implementations;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplements implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @Override
    public AccountDTO getAccount(long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.map(AccountDTO::new).orElse(null);
    }

    @Override
    public List<AccountDTO> getClientAccounts(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @Override
    public void createAccount(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
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
    }

    /**
     *
     * @return A formatted account number
     */
    private String generateAccountNumber(){
        Random random = new Random();
        int randomNumber = random.nextInt(100000000);
        String formattedNumber = String.format("%08d",randomNumber);
        return "VIN-"+formattedNumber;
    }
}
