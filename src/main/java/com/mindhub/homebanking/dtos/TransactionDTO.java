package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import java.time.LocalDate;

public class TransactionDTO {
    private long id;
    private long amount;
    private String description;
    private LocalDate date;
    private TransactionType type;

    public TransactionDTO(Transaction transaction){
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.type = transaction.getType();
        date = transaction.getDate();
    }
    public TransactionDTO(){}

    public long getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }
}
