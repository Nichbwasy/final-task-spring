package com.epam.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity(name = "blocking_request")
public class BlockingRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", nullable = false, length = 16)
    private String cardNumber;

    @Column(name = "type", nullable = false, length = 32)
    private String type;

    public BlockingRequest(String cardNumber, String type) {
        this.cardNumber = cardNumber;
        this.type = type;
    }

}
