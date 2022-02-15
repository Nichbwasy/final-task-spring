package com.epam.repositories;

import com.epam.models.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionsRepository extends CrudRepository<Transaction, Long> {
}
