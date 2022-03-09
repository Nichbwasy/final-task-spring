package com.epam.dto.cards;

import com.epam.models.CreditCard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReplenishmentFormDto {
    private CreditCard creditCard;
    private String amount;
}
