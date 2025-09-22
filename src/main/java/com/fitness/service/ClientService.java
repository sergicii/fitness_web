package com.fitness.service;

import com.fitness.dao.ClientDAO;
import com.fitness.factory.DAOFactory;

public class ClientService {
    private final ClientDAO clientDAO = DAOFactory.getDAO(ClientDAO.class);

    public ClientService() {}

}
