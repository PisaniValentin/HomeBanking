package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ClientDTO {
    private long id;
    private String firstName,lastName,email;

    private Set<AccountDTO> accounts;

    private List<ClientLoanDTO> loans;

    public ClientDTO(){}

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.loans = client.getClientLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toList());
        this.accounts = client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());
    }

    public List<ClientLoanDTO> getLoans() {
        return loans;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }
}
