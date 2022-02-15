package com.epam.repositories;

import com.epam.models.CreditCard;
import org.springframework.data.repository.CrudRepository;

public interface CreditCardRepository extends CrudRepository<CreditCard, Long> {
}
