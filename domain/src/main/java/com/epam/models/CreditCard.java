package com.epam.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "credit_cards")
@Getter @Setter @NoArgsConstructor @ToString
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "clients", nullable = false)
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "balance_id", referencedColumnName = "id")
    private Balance balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Client user;

    public CreditCard(Client user, String cardNumber, String cardExpirationMonth, String cardExpirationYear, String cvv, Boolean isLocked, Balance balance) {
        this.cardNumber = cardNumber;
        this.cardExpirationMonth = cardExpirationMonth;
        this.cardExpirationYear = cardExpirationYear;
        this.cvv = cvv;
        this.isLocked = isLocked;
        this.user = user;
        this.balance = balance;
    }

}
