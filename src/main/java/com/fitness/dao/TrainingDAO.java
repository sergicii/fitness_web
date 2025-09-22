package com.fitness.dao;

import com.fitness.model.business.Training;
import com.fitness.util.JPAInitializer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TrainingDAO {
    private static final Logger logger = LoggerFactory.getLogger(TrainingDAO.class);
    private final EntityManagerFactory emf;

    public TrainingDAO() {
        this.emf = JPAInitializer.getEntityManagerFactory();
    }

    public boolean create(Training training) {
        return JPAInitializer.executeInsideTransaction(em -> em.persist(training));
    }

    public boolean update(Training training) {
        return JPAInitializer.executeInsideTransaction(em -> em.merge(training));
    }

    public Optional<Training> findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Training training = em.find(Training.class, id);
            return Optional.ofNullable(training);
        } catch (Exception e) {
            logger.error("Error al buscar entrenamiento por ID: {}", id, e);
            return Optional.empty();
        }
    }

    public List<Training> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT t FROM Training t";
            TypedQuery<Training> query = em.createQuery(jpql, Training.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los entrenamientos.", e);
            return Collections.emptyList();
        }
    }
}
