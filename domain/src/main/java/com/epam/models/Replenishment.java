package com.epam.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.math.BigDecimal;


@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity(name = "replenishments")
public class Replenishment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_name", length = 16, nullable = false)
    private String cardNumber;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    public Replenishment(String cardNumber, BigDecimal amount) {
        this.cardNumber = cardNumber;
        this.amount = amount;
    }

}
