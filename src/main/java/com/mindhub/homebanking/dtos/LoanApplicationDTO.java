package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {
    private long loanId;
    private float amount;
    private int payments;
    private String toAccountNumber;

    public LoanApplicationDTO(long loanId, float amount, int payments, String toAccountNumber){
        this.loanId = loanId;
        this.amount = amount;
        this.payments =payments;
        this.toAccountNumber = toAccountNumber;
    }

    public long getLoanId() {
        return loanId;
    }

    public float getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }
}
