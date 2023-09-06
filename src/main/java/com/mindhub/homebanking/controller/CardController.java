package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientRepository clientRepository;

    private CardService cardService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType,
                                             @RequestParam CardColor cardColor,
                                             Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        if(client.getCards().size()>=3){
            return new ResponseEntity<>("Client already have the maximum number of cards",HttpStatus.FORBIDDEN);
        }else{
            cardService.createCard(cardType,cardColor,authentication,client);
            return new ResponseEntity<>("Card created",HttpStatus.CREATED);
        }
    }
}

