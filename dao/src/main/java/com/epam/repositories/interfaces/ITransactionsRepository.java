package com.epam.repositories.interfaces;

import com.epam.models.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface ITransactionsRepository extends CrudRepository<Transaction, Long> {
}
