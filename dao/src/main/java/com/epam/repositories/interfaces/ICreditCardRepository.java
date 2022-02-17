package com.epam.repositories.interfaces;

import com.epam.models.CreditCard;
import org.springframework.data.repository.CrudRepository;

public interface ICreditCardRepository extends CrudRepository<CreditCard, Long> {
}
