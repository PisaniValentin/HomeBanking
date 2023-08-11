package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class ClientLoan {
    @Id
    @GenericGenerator(name="native",strategy = "native")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Loan loan;

    private float amount;

    private int payments;

    public ClientLoan(){}

    public ClientLoan(Client client,Loan loan,float amount,int payments){
        this.client = client;
        this.loan = loan;
        this.amount = amount;
        this.payments = payments;
    }

    public float getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }
}
