package com.epam.repositories.interfaces;

import com.epam.models.BlockingRequest;
import org.springframework.data.repository.CrudRepository;

public interface IBlockingRequestRepository extends CrudRepository<BlockingRequest, Long> {
}
