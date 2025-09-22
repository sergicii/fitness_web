package com.fitness.dao;

import com.fitness.model.monitoring.Incident;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

public class IncidentDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Incident incident) {
        entityManager.persist(incident);
    }

    public Incident findById(Long id) {
        return entityManager.find(Incident.class, id);
    }

    public List<Incident> findAll() {
        return entityManager.createQuery("SELECT i FROM Incident i", Incident.class)
                .getResultList();
    }

    @Transactional
    public Incident update(Incident incident) {
        return entityManager.merge(incident);
    }

    @Transactional
    public void delete(Long id) {
        Incident incident = findById(id);
        if (incident != null) {
            entityManager.remove(incident);
        }
    }
}