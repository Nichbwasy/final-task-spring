package com.epam.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "balances")
//@Table(name = "balances", schema = "bank")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "amount", columnDefinition="Decimal(10,2) default '0.00'")
    private BigDecimal amount = BigDecimal.ZERO;

    protected Balance() {
    }

    public Balance(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
