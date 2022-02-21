package com.epam.repositories.classes;

import com.epam.models.Client;
import com.epam.repositories.interfaces.custom.ICustomClientRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class ClientRepositoryImpl implements ICustomClientRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Client findByLogin(String login) {
        Query query = entityManager.createQuery("SELECT * FROM bank.clients WHERE login=?", Client.class);
        query.setParameter(1, login);
        return (Client) query.getSingleResult();
    }

    @Override
    public Client findByEmail(String email) {
        Query query = entityManager.createQuery("SELECT * FROM bank.clients WHERE email=?", Client.class);
        query.setParameter(1, email);
        return (Client) query.getSingleResult();
    }
}
