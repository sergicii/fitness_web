package com.fitness.dao;

import com.fitness.model.service.Contracted;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

public class ContractedDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Contracted contracted) {
        entityManager.persist(contracted);
    }

    public Contracted findById(Long id) {
        return entityManager.find(Contracted.class, id);
    }

    public List<Contracted> findAll() {
        return entityManager.createQuery("SELECT c FROM Contracted c", Contracted.class)
                .getResultList();
    }

    @Transactional
    public Contracted update(Contracted contracted) {
        return entityManager.merge(contracted);
    }

    @Transactional
    public void delete(Long id) {
        Contracted contracted = findById(id);
        if (contracted != null) {
            entityManager.remove(contracted);
        }
    }
}