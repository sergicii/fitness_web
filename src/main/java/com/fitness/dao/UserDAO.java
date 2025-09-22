package com.fitness.dao;

import com.fitness.util.JPAInitializer;
import com.fitness.model.user.User;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class UserDAO {
    private final EntityManagerFactory emf;
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public UserDAO() {
        this.emf = JPAInitializer.getEntityManagerFactory();
    }

    public boolean create(User user) {
        return JPAInitializer.executeInsideTransaction(em -> em.persist(user));
    }

    public boolean update(User user) {
        return JPAInitializer.executeInsideTransaction(em -> em.merge(user));
    }

    public Optional<User> findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            User user = em.find(User.class, id);
            return Optional.ofNullable(user);
        }
    }

    public Optional<User> findByEmail(String email) {
        try (EntityManager em = emf.createEntityManager()) {
            String sql = "SELECT u FROM User u WHERE u.email = :email";
            TypedQuery<User> query = em.createQuery(sql, User.class);
            query.setParameter("email", email);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<User> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            String sql = "SELECT u FROM User u";
            TypedQuery<User> query = em.createQuery(sql, User.class);
            return query.getResultList();
        }
    }
}
