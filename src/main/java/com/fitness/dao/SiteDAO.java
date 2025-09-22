package com.fitness.dao;

import com.fitness.model.business.Site;
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

public class SiteDAO {
    private final EntityManagerFactory emf;
    private static final Logger logger = LoggerFactory.getLogger(SiteDAO.class);

    public SiteDAO() {
        emf = JPAInitializer.getEntityManagerFactory();
    }

    public boolean create(Site site) {
        return JPAInitializer.executeInsideTransaction(em -> em.persist(site));
    }

    public Optional<Site> findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Site site = em.find(Site.class, id);
            return Optional.ofNullable(site);
        } catch (Exception e) {
            logger.error("Error al buscar lugar por ID: {}", id, e);
            return Optional.empty();
        }
    }

    public List<Site> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT s FROM Site s";
            TypedQuery<Site> query = em.createQuery(jpql, Site.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los lugares.", e);
            return Collections.emptyList();
        }
    }
}
