package com.epam.repositories;

import com.epam.models.BlockingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockingRequestRepository extends JpaRepository<BlockingRequest, Long> {
}
