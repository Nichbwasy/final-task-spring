package com.epam.dto.cards;

import com.epam.models.CreditCard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class TransactionFormDto {
    private CreditCard senderCard;
    private String amount;
    private String recipientCardNumber;
}
