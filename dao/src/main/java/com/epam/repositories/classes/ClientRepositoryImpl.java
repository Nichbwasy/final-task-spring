package com.epam.repositories.classes;

import com.epam.models.Client;
import com.epam.repositories.interfaces.IClientRepository;
import com.epam.repositories.interfaces.custom.ICustomClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class ClientRepositoryImpl implements ICustomClientRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Client findAllByLogin(String login) {
        Query query = entityManager.createQuery("SELECT * FROM bank.clients WHERE login=?", Client.class);
        query.setParameter(1, login);
        return (Client) query.getSingleResult();
    }
}
