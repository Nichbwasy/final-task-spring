package com.epam.models;

import javax.persistence.*;

@Entity(name = "blocking_request")
//@Table(name = "blocking_request", schema = "bank")
public class BlockingRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "type", nullable = false)
    private Integer type;

    protected BlockingRequest() {
    }

    public BlockingRequest(CreditCard creditCard, Integer type) {
        //this.creditCard = creditCard;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BlockingRequest{" +
                "id=" + id +
                ", type=" + type +
                '}';
    }
}
