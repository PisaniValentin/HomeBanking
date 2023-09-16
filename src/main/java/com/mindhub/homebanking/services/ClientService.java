package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ClientService {
    public List<ClientDTO> getAllClients();

    public ClientDTO getClientById(long id);

    public void registerNewClient( String firstName,String lastName,String email, String password);

    public ClientDTO getLoggedClientDTO(Authentication authentication);
}
