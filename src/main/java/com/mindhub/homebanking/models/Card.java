package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GenericGenerator(name="native",strategy = "native")
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    private CardColor color;
    private CardType type;
    private String cardholder;
    private String number;
    private int cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;

    public Card(){}

    public Card(CardColor color, CardType type, String cardholder,
                String number, int cvv, LocalDate fromDate, LocalDate thruDate) {
        this.color = color;
        this.type = type;
        this.cardholder = cardholder;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
    }

    public long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public CardColor getColor() {
        return color;
    }

    public CardType getType() {
        return type;
    }

    public String getCardholder() {
        return cardholder;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }
}
