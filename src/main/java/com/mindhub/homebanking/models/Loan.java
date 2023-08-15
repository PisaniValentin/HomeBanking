package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GenericGenerator(name="native",strategy = "native")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private long id;

    private String name;
    private float maxAmount;

    @OneToMany(mappedBy = "loan",fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @ElementCollection
    private List<Integer> payments = new LinkedList<>();

    public Loan(){}

    public Loan(String name, float maxAmount){
        this.name = name;
        this.maxAmount = maxAmount;
    }

    public void addClientLoan(ClientLoan clientLoan){
        this.clientLoans.add(clientLoan);
        clientLoan.setLoan(this);
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    @JsonIgnore
    public List<Client> getClients(){
        return clientLoans.stream().map(elem -> elem.getClient()).collect(Collectors.toList());
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(float maxAmount) {
        this.maxAmount = maxAmount;
    }

    public long getId() {
        return id;
    }
}
