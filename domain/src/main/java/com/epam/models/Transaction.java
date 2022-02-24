package com.epam.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "transactions")
@Getter @Setter @NoArgsConstructor @ToString
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_card_number", length = 16, nullable = false)
    private String senderCardNumber;

    @Column(name = "recipient_card_number", length = 16, nullable = false)
    private String recipientCardNumber;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    public Transaction(String senderCardNumber, String recipientCardNumber, BigDecimal amount) {
        this.senderCardNumber = senderCardNumber;
        this.recipientCardNumber = recipientCardNumber;
        this.amount = amount;
    }
}
