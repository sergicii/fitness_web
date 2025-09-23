package com.fitness.dao;

import com.fitness.model.user.Client;
import com.fitness.model.user.Employee;
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

    public Optional<Employee> findByUserId(Long userId) {
        // Usamos try-with-resources para asegurar que el EntityManager se cierre siempre.
        try (EntityManager em = emf.createEntityManager()) {
            // 1. El Query JPQL: "Selecciona el empleado 'e' donde el ID del usuario asociado a 'e' sea :userId"
            String jpql = "SELECT e FROM Employee e WHERE e.user.id = :userId";

            // 2. Creamos la consulta tipada para evitar casteos.
            TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);

            // 3. Asignamos el valor del parámetro para evitar inyección SQL.
            query.setParameter("userId", userId);

            // 4. Ejecutamos la consulta y devolvemos el resultado.
            // Usamos getSingleResult porque la relación User-Employee debería ser única.
            return Optional.of(query.getSingleResult());

        } catch (NoResultException e) {
            // Si no se encuentra ningún empleado para ese usuario, es una situación normal, no un error.
            // Devolvemos un Optional vacío para indicarlo.
            logger.warn("No se encontró ningún empleado para el ID de usuario: {}", userId);
            return Optional.empty();
        } catch (Exception e) {
            // Capturamos cualquier otro error inesperado durante la consulta.
            logger.error("Error al buscar empleado por ID de usuario: {}", userId, e);
            return Optional.empty();
        }
    }

    public Optional<Client> findByUserIdClient(Long userId) {
        // Usamos try-with-resources para asegurar que el EntityManager se cierre siempre.
        try (EntityManager em = emf.createEntityManager()) {
            // 1. El Query JPQL: "Selecciona el cliente 'c' donde el ID del usuario asociado a 'c' sea :userId"
            String jpql = "SELECT c FROM Client c WHERE c.user.id = :userId";

            // 2. Creamos la consulta tipada.
            TypedQuery<Client> query = em.createQuery(jpql, Client.class);

            // 3. Asignamos el valor del parámetro.
            query.setParameter("userId", userId);

            // 4. Ejecutamos la consulta y devolvemos el resultado.
            // Usamos getSingleResult porque la relación User-Client debería ser única.
            return Optional.of(query.getSingleResult());

        } catch (NoResultException e) {
            // Si no se encuentra ningún cliente para ese usuario, es normal. Devolvemos un Optional vacío.
            logger.warn("No se encontró ningún cliente para el ID de usuario: {}", userId);
            return Optional.empty();
        } catch (Exception e) {
            // Capturamos cualquier otro error inesperado.
            logger.error("Error al buscar cliente por ID de usuario: {}", userId, e);
            return Optional.empty();
        }
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
