package com.epam.repositories.interfaces;

import com.epam.models.Client;
import com.epam.repositories.interfaces.custom.ICustomClientRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends CrudRepository<Client, Long>, ICustomClientRepository {

}

