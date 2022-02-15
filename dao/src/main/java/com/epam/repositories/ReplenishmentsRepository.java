package com.epam.repositories;

import com.epam.models.Replenishment;
import org.springframework.data.repository.CrudRepository;

public interface ReplenishmentsRepository extends CrudRepository<Replenishment, Long> {
}
