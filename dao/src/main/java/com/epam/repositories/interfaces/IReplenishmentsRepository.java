package com.epam.repositories.interfaces;

import com.epam.models.Replenishment;
import org.springframework.data.repository.CrudRepository;

public interface IReplenishmentsRepository extends CrudRepository<Replenishment, Long> {
}
