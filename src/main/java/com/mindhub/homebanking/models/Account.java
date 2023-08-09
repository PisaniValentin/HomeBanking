package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GenericGenerator(name="native",strategy = "native")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private long id;
    private String number;
    private LocalDate date;
    private double balance;

    public Account(){}

    public Account(String number, LocalDate creationDate, double balance){
        this.number = number;
        this.date = creationDate;
        this.balance = balance;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private Set<Transaction> transactions = new HashSet<>();

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getBalance() {
        return balance;
    }

    public long getId() {
        return id;
    }
}
