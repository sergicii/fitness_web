package com.fitness.service;

import com.fitness.dao.ClientDAO;
import com.fitness.factory.DAOFactory;
import com.fitness.model.user.Client;

import java.util.List;
import java.util.Optional;

public class ClientService {
    private final ClientDAO clientDAO = DAOFactory.getDAO(ClientDAO.class);

    public ClientService() {}

    public boolean addClient(Client client) {
        return clientDAO.create(client);
    }

    public boolean update(Client client) {
        return clientDAO.update(client);
    }

    public Optional<Client> getClientById(Long id) {
        return clientDAO.findById(id);
    }

    public Optional<Client> getClientByDni(String dni) {
        return clientDAO.findByDni(dni);
    }

    public List<Client> getAllClients() {
        return clientDAO.findAll();
    }
}
