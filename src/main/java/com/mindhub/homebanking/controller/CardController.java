package com.mindhub.homebanking.controller;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    ClientRepository clientRepository;

    @RequestMapping(path = "/clients/current/cards",method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType,
                                             @RequestParam CardColor cardColor,
                                             Authentication authentication){
        Random random = new Random();
        int cvvNumber = random.nextInt(1000);
        Client client = clientRepository.findByEmail(authentication.getName());
        if(client.getCards().size()>=3){
            return new ResponseEntity<>("forbidden",HttpStatus.FORBIDDEN);
        }else{
            String cardNumber = generateCardNumber();
            boolean repeatedCardNumber = true;
            while(repeatedCardNumber){
                if(cardRepository.findByNumber(cardNumber) != null){
                    cardNumber = generateCardNumber();
                }else{
                    repeatedCardNumber = false;
                }
            }
            int cvv = Integer.parseInt((String.format("%03d",cvvNumber)));
            Card card = new Card(cardColor,cardType,client.getFirstName()+" "+client.getLastName(),
                    cardNumber,cvv, LocalDate.now(),LocalDate.now().plusYears(5));
            client.addCard(card);
            card.setClient(client);
            cardRepository.save(card);
            clientRepository.save(client);
            return new ResponseEntity<>("created",HttpStatus.CREATED);
        }
    }

    /**
     *
     * @return A valid and unique card number
     */
    private String generateCardNumber() {
        Random random = new Random();
        int randomNumber1 = random.nextInt(10000);
        int randomNumber2 = random.nextInt(10000);
        int randomNumber3 = random.nextInt(10000);
        int randomNumber4 = random.nextInt(10000);
        String formattedNumber1 = String.format("%04d",randomNumber1);
        String formattedNumber2 = String.format("%04d",randomNumber2);
        String formattedNumber3 = String.format("%04d",randomNumber3);
        String formattedNumber4 = String.format("%04d",randomNumber4);
        return formattedNumber1+"-"+formattedNumber2+"-"+formattedNumber3+"-"+formattedNumber4;
    }

}

