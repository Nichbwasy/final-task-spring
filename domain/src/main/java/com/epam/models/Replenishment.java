package com.epam.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "replenishments")
//@Table(name = "replenishments", schema = "bank")
public class Replenishment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "card_name", length = 16, nullable = false)
    private String cardNumber;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    protected Replenishment() {
    }

    public Replenishment(String cardNumber, BigDecimal amount) {
        this.cardNumber = cardNumber;
        this.amount = amount;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Replenishment{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", amount=" + amount +
                '}';
    }
}
