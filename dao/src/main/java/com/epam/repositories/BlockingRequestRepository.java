package com.epam.repositories;

import com.epam.models.BlockingRequest;
import org.springframework.data.repository.CrudRepository;

public interface BlockingRequestRepository extends CrudRepository<BlockingRequest, Long> {
}
