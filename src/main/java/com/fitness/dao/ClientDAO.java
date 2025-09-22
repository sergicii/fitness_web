package com.fitness.dao;

import com.fitness.model.user.Client;
import com.fitness.util.JPAInitializer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ClientDAO {
    private final EntityManagerFactory emf;
    private static final Logger logger = LoggerFactory.getLogger(ClientDAO.class);

    public ClientDAO() {
        emf = JPAInitializer.getEntityManagerFactory();
    }

    public boolean create(Client client) {
        return JPAInitializer.executeInsideTransaction(em -> em.persist(client));
    }

    public boolean update(Client client) {
        return JPAInitializer.executeInsideTransaction(em -> em.merge(client));
    }

    public Optional<Client> findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Client client = em.find(Client.class, id);
            return Optional.ofNullable(client);
        } catch (Exception e) {
            logger.error("Error al buscar cliente por ID: {}", id, e);
            return Optional.empty();
        }
    }

    public Optional<Client> findByDni(String dni) {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT c FROM Client c WHERE c.dni = :dni";
            TypedQuery<Client> query = em.createQuery(jpql, Client.class);
            query.setParameter("dni", dni);
            return query.getResultStream().findFirst();
        } catch (Exception e) {
            logger.error("Error al buscar cliente por dni: {}", dni, e);
            return Optional.empty();
        }
    }

    public List<Client> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT c FROM Client c";
            TypedQuery<Client> query = em.createQuery(jpql, Client.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los clientes.", e);
            return Collections.emptyList();
        }
    }
}
