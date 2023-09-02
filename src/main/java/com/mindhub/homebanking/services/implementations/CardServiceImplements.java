package com.mindhub.homebanking.services.implementations;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Random;

@Service
public class CardServiceImplements implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void createCard(CardType cardType, CardColor cardColor, Authentication authentication, Client client) {
        Random random = new Random();
        int cvvNumber = random.nextInt(1000);
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
