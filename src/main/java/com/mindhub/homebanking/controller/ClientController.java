package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;


    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.map(ClientDTO::new).orElse(null);
    }

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password,Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(firstName.contains(" ") || lastName.contains(" ") || email.contains(" ") || password.contains(" ")){
            return new ResponseEntity<>("White space not allowed", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));
        //Create account and assign it to the logged Client
        Account account = new Account(generateAccountNumber(), LocalDate.now(),0);
        account.setClient(client);
        client.addAccount(account);
        accountRepository.save(account);
        clientRepository.save(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
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

    @RequestMapping("/clients/current")
    public ClientDTO getClientDTO(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        return new ClientDTO(client);
    }
}
