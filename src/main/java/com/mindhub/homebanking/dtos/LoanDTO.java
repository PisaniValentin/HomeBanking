package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LoanDTO {
    private String name;
    private float maxAmount;
    private Set<ClientLoanDTO> loan;
    private List<Integer> payments;
    private long id;

    public LoanDTO(Loan loan){
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.loan = loan.getClientLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toSet());
        this.payments = loan.getPayments();
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public float getMaxAmount() {
        return maxAmount;
    }

    public Set<ClientLoanDTO> getLoan() {
        return loan;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
