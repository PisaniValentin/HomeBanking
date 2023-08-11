package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private long id;
    private String firstName,lastName,email;

    public Client(){}

    public Client (String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client")
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoanSet = new HashSet<>();

    public void addClientLoan(ClientLoan clientLoan){
        clientLoanSet.add(clientLoan);
        clientLoan.setClient(this);
    }

    public List<Loan> getLoans(){
        return clientLoanSet.stream().map(elem -> elem.getLoan()).collect(Collectors.toList());
    }


    public Set<Account> getAccounts(){
        return accounts;
    }

    public void addAccount(Account account){
        accounts.add(account);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String toString() {
        return firstName + " " + lastName;
    }

    public long getId() {
        return id;
    }
}
