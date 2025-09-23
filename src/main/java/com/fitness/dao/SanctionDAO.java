package com.fitness.dao;

import com.fitness.model.monitoring.Sanction; // Asegúrate de que esta ruta sea correcta
import com.fitness.util.JPAInitializer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SanctionDAO {
    private final EntityManagerFactory emf;
    private static final Logger logger = LoggerFactory.getLogger(SanctionDAO.class);

    public SanctionDAO() {
        this.emf = JPAInitializer.getEntityManagerFactory();
    }

    public boolean create(Sanction sanction) {
        return JPAInitializer.executeInsideTransaction(em -> em.persist(sanction));
    }

    public boolean update(Sanction sanction) {
        return JPAInitializer.executeInsideTransaction(em -> em.merge(sanction));
    }

    public boolean delete(Long sanctionId) {
        return JPAInitializer.executeInsideTransaction(em -> {
            Sanction sanction = em.find(Sanction.class, sanctionId);
            if (sanction != null) {
                em.remove(sanction);
            }
        });
    }

    public Optional<Sanction> findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Sanction sanction = em.find(Sanction.class, id);
            return Optional.ofNullable(sanction);
        } catch (Exception e) {
            logger.error("Error al buscar sanción por ID: {}", id, e);
            return Optional.empty();
        }
    }

    public List<Sanction> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT s FROM Sanction s";
            TypedQuery<Sanction> query = em.createQuery(jpql, Sanction.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todas las sanciones.", e);
            return Collections.emptyList();
        }
    }
}