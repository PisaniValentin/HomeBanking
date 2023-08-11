package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GenericGenerator(name="native",strategy = "native")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private long id;

    private String name;
    private float maxAmount;

    public Loan(){}

    public Loan(String name, float maxAmount){
        this.name = name;
        this.maxAmount = maxAmount;
    }

    @OneToMany(mappedBy = "loan",fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoanSet = new HashSet<>();

    public void addClientLoan(ClientLoan clientLoan){
        clientLoanSet.add(clientLoan);
        clientLoan.setLoan(this);
    }

    @ElementCollection
    @Column(name = "payments")
    private List<Integer> payments = new LinkedList<>();

    public List<Integer> getPayments() {
        return payments;
    }


    public List<Client> getClients(){
        return clientLoanSet.stream().map(elem -> elem.getClient()).collect(Collectors.toList());
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
