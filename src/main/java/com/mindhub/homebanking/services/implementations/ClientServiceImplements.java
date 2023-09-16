package com.mindhub.homebanking.services.implementations;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplements implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @Override
    public ClientDTO getClientById(long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.map(ClientDTO::new).orElse(null);
    }

    @Override
    public void registerNewClient(String firstName,String lastName,String email, String password) {
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        client.setAuthority("CLIENT");
        clientRepository.save(client);
        //Create account and assign it to the logged Client
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
        account.setClient(client);
        client.addAccount(account);
        accountRepository.save(account);
        clientRepository.save(client);
    }

    @Override
    public ClientDTO getLoggedClientDTO(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        return new ClientDTO(client);
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
