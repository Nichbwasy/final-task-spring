package com.epam.repositories;

import com.epam.models.Replenishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplenishmentsRepository extends JpaRepository<Replenishment, Long> {
}
