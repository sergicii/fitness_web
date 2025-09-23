package com.fitness.dao;

import com.fitness.model.service.Membership;
import com.fitness.util.JPAInitializer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MembershipDAO {
    private final EntityManagerFactory emf;
    private static final Logger logger = LoggerFactory.getLogger(MembershipDAO.class);

    public MembershipDAO() {
        this.emf = JPAInitializer.getEntityManagerFactory();
    }

    public boolean create(Membership membership) {
        return JPAInitializer.executeInsideTransaction(em -> em.persist(membership));
    }

    public Optional<Membership> findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Membership membership = em.find(Membership.class, id);
            return Optional.ofNullable(membership);
        } catch (Exception e) {
            logger.error("Error al buscar membresía por ID: {}", id, e);
            return Optional.empty();
        }
    }

    public List<Membership> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT m FROM Membership m";
            TypedQuery<Membership> query = em.createQuery(jpql, Membership.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todas las membresías.", e);
            return Collections.emptyList();
        }
    }
}