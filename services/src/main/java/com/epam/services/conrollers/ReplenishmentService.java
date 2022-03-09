package com.epam.services.conrollers;

import com.epam.models.Client;
import com.epam.models.CreditCard;
import com.epam.models.Replenishment;

import java.math.BigDecimal;
import java.util.List;

public interface ReplenishmentService {
    List<Replenishment> showReplenishmentHistory(Integer page);
    Replenishment balanceReplenishment(Client client, CreditCard creditCard, String amount);
    Boolean cancelOperation(Long id);
}
