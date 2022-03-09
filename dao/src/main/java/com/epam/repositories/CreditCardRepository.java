package com.epam.repositories;

import com.epam.models.Client;
import com.epam.models.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    CreditCard getByCardNumber(String cardNumber);
    List<CreditCard> findByClient(Client client);
    Boolean existsByCardNumber(String cardNumber);
    void deleteByCardNumber(String cardNumber);
}
