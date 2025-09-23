package com.fitness.service;

import com.fitness.dao.ContractedDAO;
import com.fitness.factory.DAOFactory;
import com.fitness.model.service.Contracted;

import java.util.List;
import java.util.Optional;

public class ContractedService {
    private final ContractedDAO contractedDAO = DAOFactory.getDAO(ContractedDAO.class);

    public boolean addContracted(Contracted contracted) {
        return contractedDAO.create(contracted);
    }

    public boolean updateContracted(Contracted contracted) {
        return contractedDAO.update(contracted);
    }

    public List<Contracted> getAllContracted() {
        return contractedDAO.findAll();
    }
}
