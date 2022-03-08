package com.epam.repositories;

import com.epam.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Client getByUsername(String username);
    Client getByEmail(String username);
    List<Client> findByUsername(String username);
}

