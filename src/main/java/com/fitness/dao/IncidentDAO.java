package com.fitness.dao;

import com.fitness.model.monitoring.Incident;
import com.fitness.util.JPAInitializer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class IncidentDAO {
    private final EntityManagerFactory emf;
    private static final Logger logger = LoggerFactory.getLogger(IncidentDAO.class);

    public IncidentDAO() {
        this.emf = JPAInitializer.getEntityManagerFactory();
    }

    public boolean create(Incident incident) {
        return JPAInitializer.executeInsideTransaction(em -> em.persist(incident));
    }

    public boolean update(Incident incident) {
        return JPAInitializer.executeInsideTransaction(em -> em.merge(incident));
    }

    public boolean delete(Long incidentId) {
        return JPAInitializer.executeInsideTransaction(em -> {
            Incident incident = em.find(Incident.class, incidentId);
            if (incident != null) {
                em.remove(incident);
            }
        });
    }

    public Optional<Incident> findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Incident incident = em.find(Incident.class, id);
            return Optional.ofNullable(incident);
        } catch (Exception e) {
            logger.error("Error al buscar incidente por ID: {}", id, e);
            return Optional.empty();
        }
    }

    public List<Incident> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT i FROM Incident i";
            TypedQuery<Incident> query = em.createQuery(jpql, Incident.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los incidentes.", e);
            return Collections.emptyList();
        }
    }
}