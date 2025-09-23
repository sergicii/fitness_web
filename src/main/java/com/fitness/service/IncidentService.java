package com.fitness.service;

import com.fitness.dao.IncidentDAO;
import com.fitness.factory.DAOFactory;
import com.fitness.model.monitoring.Incident;

import java.util.Comparator;
import java.util.List;

public class IncidentService {
    private final IncidentDAO incidentDAO = DAOFactory.getDAO(IncidentDAO.class);

    public boolean addIncident(Incident incident) {
        return incidentDAO.create(incident);
    }

    public List<Incident> getIncidents() {
        List<Incident> incidents = incidentDAO.findAll();
        incidents.sort(Comparator.comparing(Incident::getDate).reversed());
        return incidents;
    }
}
