package com.epam.models;

import javax.persistence.*;

@Entity(name = "blocking_request")
public class BlockingRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;

    @Column(name = "type", nullable = false)
    private Integer type;

    protected BlockingRequest() {
    }

    public BlockingRequest(CreditCard creditCard, Integer type) {
        this.creditCard = creditCard;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Integer getType() {
        return type;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BlockingRequest{" +
                "id=" + id +
                ", creditCard=" + creditCard +
                ", type=" + type +
                '}';
    }
}
