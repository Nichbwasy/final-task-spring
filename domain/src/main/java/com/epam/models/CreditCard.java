package com.epam.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity(name = "credit_cards")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "This field is compulsory")
    @Length(min = 16, max = 16, message = "Credit card number must have 16 numbers!")
    @Column(name = "card_number", nullable = false, unique = true, length = 16)
    private String cardNumber;

    @NotNull(message = "This field is compulsory")
    @Length(min = 2, max = 2, message = "Credit card expiration month must have 2 numbers!")
    @Column(name = "card_expiration_month", nullable = false, length = 2)
    private String cardExpirationMonth;

    @NotNull(message = "This field is compulsory")
    @Length(min = 2, max = 2, message = "Credit card expiration year must have 2 numbers!")
    @Column(name = "card_expiration_year", nullable = false, length = 2)
    private String cardExpirationYear;

    @NotNull
    @Length(min = 3, max = 3, message = "Credit card CVV must have 3 numbers!")
    @Column(name = "cvv", nullable = false, length = 3)
    private String cvv;

    @Column(name = "locked", columnDefinition = "boolean default false")
    private Boolean isLocked;

    @OneToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    @JoinColumn(name = "balance_id", referencedColumnName = "id")
    private Balance balance;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "client_id")
    private Client client;

    public CreditCard(Client client, String cardNumber, String cardExpirationMonth, String cardExpirationYear, String cvv, Boolean isLocked, Balance balance) {
        this.cardNumber = cardNumber;
        this.cardExpirationMonth = cardExpirationMonth;
        this.cardExpirationYear = cardExpirationYear;
        this.cvv = cvv;
        this.isLocked = isLocked;
        this.client = client;
        this.balance = balance;
    }

}
