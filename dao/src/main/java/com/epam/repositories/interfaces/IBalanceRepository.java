package com.epam.repositories.interfaces;

import com.epam.models.Balance;
import org.springframework.data.repository.CrudRepository;

public interface IBalanceRepository extends CrudRepository<Balance, Long> {
}
