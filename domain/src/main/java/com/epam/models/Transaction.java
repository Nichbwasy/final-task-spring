package com.epam.models;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "transactions")
@EnableAutoConfiguration
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

    protected Transaction() {
    }

    public Transaction(String senderCardNumber, String recipientCardNumber, BigDecimal amount) {
        this.senderCardNumber = senderCardNumber;
        this.recipientCardNumber = recipientCardNumber;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public String getSenderCardNumber() {
        return senderCardNumber;
    }

    public void setSenderCardNumber(String senderCardNumber) {
        this.senderCardNumber = senderCardNumber;
    }

    public String getRecipientCardNumber() {
        return recipientCardNumber;
    }

    public void setRecipientCardNumber(String recipientCardNumber) {
        this.recipientCardNumber = recipientCardNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", senderCardNumber='" + senderCardNumber.trim() + '\'' +
                ", recipientCardNumber='" + recipientCardNumber.trim() + '\'' +
                ", amount=" + amount +
                '}';
    }
}
