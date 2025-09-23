package com.fitness.dao;

import com.fitness.model.service.Contracted;
import com.fitness.util.JPAInitializer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ContractedDAO {
    private final EntityManagerFactory emf;
    private static final Logger logger = LoggerFactory.getLogger(ContractedDAO.class);

    public ContractedDAO() {
        this.emf = JPAInitializer.getEntityManagerFactory();
    }

    public boolean create(Contracted contracted) {
        return JPAInitializer.executeInsideTransaction(em -> em.persist(contracted));
    }

    public boolean update(Contracted contracted) {
        return JPAInitializer.executeInsideTransaction(em -> em.merge(contracted));
    }

    public boolean delete(Long contractedId) {
        return JPAInitializer.executeInsideTransaction(em -> {
            Contracted contracted = em.find(Contracted.class, contractedId);
            if (contracted != null) {
                em.remove(contracted);
            }
        });
    }

    public Optional<Contracted> findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Contracted contracted = em.find(Contracted.class, id);
            return Optional.ofNullable(contracted);
        } catch (Exception e) {
            logger.error("Error al buscar servicio contratado por ID: {}", id, e);
            return Optional.empty();
        }
    }

    public List<Contracted> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT c FROM Contracted c";
            TypedQuery<Contracted> query = em.createQuery(jpql, Contracted.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los servicios contratados.", e);
            return Collections.emptyList();
        }
    }
}