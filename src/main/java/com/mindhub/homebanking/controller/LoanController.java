package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LoanService loanService;

    @RequestMapping(path = "/loans",method = RequestMethod.GET)
    public List<LoanDTO> getLoan(){
        return loanService.getLoan();
    }

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
                return new ResponseEntity<>("Amount is higher than the maximum amount possible", HttpStatus.FORBIDDEN);
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
                            loanService.createLoan(loanAppDTO,loggedUser,account,existentLoan);
                            return new ResponseEntity<>("Loan created", HttpStatus.CREATED);
                        }
                    }
                }
            }
        }
    }
}
