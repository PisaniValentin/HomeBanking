package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface LoanService {
    public List<LoanDTO> getLoan();
    public void createLoan(LoanApplicationDTO loanAppDTO, Authentication authentication,
                           Client loggedUser, Account account, Loan loan);
}
