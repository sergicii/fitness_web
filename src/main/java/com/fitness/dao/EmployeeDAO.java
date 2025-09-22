package com.fitness.dao;

import com.fitness.model.user.Employee;
import com.fitness.util.JPAInitializer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EmployeeDAO {
    private final EntityManagerFactory emf;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeDAO.class);

    public EmployeeDAO() {
        emf = JPAInitializer.getEntityManagerFactory();
    }

    public boolean create(Employee employee) {
        return JPAInitializer.executeInsideTransaction(em -> em.persist(employee));
    }

    public boolean update(Employee employee) {
        return JPAInitializer.executeInsideTransaction(em -> em.merge(employee));
    }

    public Optional<Employee> findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Employee employee = em.find(Employee.class, id);
            return Optional.ofNullable(employee);
        } catch (Exception e) {
            logger.error("Error al buscar empleado por ID: {}", id, e);
            return Optional.empty();
        }
    }

    public Optional<Employee> findByDni(String dni) {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT c FROM Employee c WHERE c.dni = :dni";
            TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
            query.setParameter("dni", dni);
            return query.getResultStream().findFirst();
        } catch (Exception e) {
            logger.error("Error al buscar empleado por dni: {}", dni, e);
            return Optional.empty();
        }
    }

    public List<Employee> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT c FROM Employee c";
            TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los empleados.", e);
            return Collections.emptyList();
        }
    }
}
