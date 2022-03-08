package com.epam.services.conrollers;

import com.epam.models.CreditCard;
import com.epam.models.Replenishment;

import java.math.BigDecimal;
import java.util.List;

public interface ReplenishmentService {
    List<Replenishment> showReplenishmentHistory(Integer page);
    Boolean balanceReplenishment(CreditCard creditCard, BigDecimal amount);
    Boolean cancelOperation(Long id);
}
