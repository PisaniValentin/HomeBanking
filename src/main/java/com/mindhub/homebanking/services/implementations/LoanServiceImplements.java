package com.mindhub.homebanking.services.implementations;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplements implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<LoanDTO> getLoan() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Override
    public void createLoan(LoanApplicationDTO loanAppDTO,Client loggedUser, Account account, Loan loan) {
        //if the account belong to the logged user
        ClientLoan clientLoan = new ClientLoan(loanAppDTO.getAmount()+ (loanAppDTO.getAmount()*20/100), loanAppDTO.getPayments());
        clientLoan.setClient(loggedUser);
        loggedUser.addClientLoan(clientLoan);
        loan.addClientLoan(clientLoan);
        Transaction transaction = new Transaction(loanAppDTO.getAmount(),
                loan.getName()+" Loan approved",TransactionType.CREDIT);
        account.setBalance(account.getBalance()+ loanAppDTO.getAmount());
        accountRepository.save(account);
        transactionRepository.save(transaction);
        clientRepository.save(loggedUser);
        clientLoanRepository.save(clientLoan);

    }
}



