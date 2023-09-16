package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private long loanId,id;
    private String name;
    private float amount;
    private int payments;

    public ClientLoanDTO(ClientLoan clientLoan){
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
    }

    public long getLoanId() {
        return loanId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }
}
