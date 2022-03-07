package com.epam.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity(name = "balances")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", columnDefinition="Decimal(10,2) default '0.00'")
    private BigDecimal amount = BigDecimal.ZERO;

    public Balance(BigDecimal amount) {
        this.amount = amount;
    }
}
