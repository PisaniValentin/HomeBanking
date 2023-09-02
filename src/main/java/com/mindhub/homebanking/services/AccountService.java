package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {
    public List<AccountDTO> getAccounts();

    public AccountDTO getClient( long id);

    public List<AccountDTO> getClientAccounts(Authentication authentication);

    public void createAccount(Authentication authentication);
}
