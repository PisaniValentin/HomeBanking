package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    @RequestMapping(path="/loans", method = RequestMethod.POST)
    public ResponseEntity<Object> createLoan(@RequestBody LoanApplicationDTO loanAppDTO, Authentication authentication){
        if(loanAppDTO.getToAccountNumber().isEmpty()|| loanAppDTO.getAmount() == 0
                || loanAppDTO.getPayments() == 0){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        Optional<Loan> loan = loanRepository.findById(loanAppDTO.getLoanId());
        if (!loan.isPresent()) {
            return new ResponseEntity<>("Loan not found", HttpStatus.FORBIDDEN);
        }else{
            Loan existentLoan = loan.get();
            if(existentLoan.getMaxAmount() < loanAppDTO.getAmount()){
                return new ResponseEntity<>("Amount is greater than the maximum amount", HttpStatus.FORBIDDEN);
            }else{
                //if the loan amount is valid, check if the value of the payments is valid
                if(!existentLoan.getPayments().contains(loanAppDTO.getPayments())){
                    return new ResponseEntity<>("Payment is not valid", HttpStatus.FORBIDDEN);
                }else{
                    //If the payment value is valid, check if destiny account exist and belong to the logged user
                    Account account = accountRepository.findByNumber(loanAppDTO.getToAccountNumber());
                    if(account == null){
                        return new ResponseEntity<>("Account not found", HttpStatus.FORBIDDEN);
                    }else{
                        Client loggedUser = clientRepository.findByEmail(authentication.getName());
                        if(!loggedUser.getAccounts().contains(account)){
                            return new ResponseEntity<>("User is not the owner of this account", HttpStatus.FORBIDDEN);
                        }else{
                            //if the account belong to the logged user

                        }

                    }

                }

            }

        }


    }

}
