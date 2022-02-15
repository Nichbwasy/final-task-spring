package com.epam.models;

import javax.persistence.*;

@Entity(name = "credit_cards")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "card_number", nullable = false, unique = true, length = 16)
    private String cardNumber;

    @Column(name = "card_expiration_month", nullable = false, length = 2)
    private String cardExpirationMonth;

    @Column(name = "card_expiration_year", nullable = false, length = 2)
    private String cardExpirationYear;

    @Column(name = "cvv", nullable = false, length = 3)
    private String cvv;

    @Column(name = "locked", columnDefinition = "boolean default false")
    private Boolean isLocked;

    @OneToOne
    @JoinColumn(name = "balance_id")
    private Balance balance;

    public CreditCard() {
    }

    public CreditCard(String cardNumber, String cardExpirationMonth, String cardExpirationYear, String cvv, Boolean isLocked, Balance balance) {
        this.cardNumber = cardNumber;
        this.cardExpirationMonth = cardExpirationMonth;
        this.cardExpirationYear = cardExpirationYear;
        this.cvv = cvv;
        this.isLocked = isLocked;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpirationMonth() {
        return cardExpirationMonth;
    }

    public void setCardExpirationMonth(String cardExpirationMonth) {
        this.cardExpirationMonth = cardExpirationMonth;
    }

    public String getCardExpirationYear() {
        return cardExpirationYear;
    }

    public void setCardExpirationYear(String cardExpirationYear) {
        this.cardExpirationYear = cardExpirationYear;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardExpirationMonth='" + cardExpirationMonth + '\'' +
                ", cardExpirationYear='" + cardExpirationYear + '\'' +
                ", cvv='" + cvv + '\'' +
                ", isLocked=" + isLocked +
                '}';
    }
}
