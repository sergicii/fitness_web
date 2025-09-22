package com.fitness.dao;

import com.fitness.model.monitoring.Sanction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

public class SanctionDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Sanction sanction) {
        entityManager.persist(sanction);
    }

    public Sanction findById(Long id) {
        return entityManager.find(Sanction.class, id);
    }

    public List<Sanction> findAll() {
        return entityManager.createQuery("SELECT s FROM Sanction s", Sanction.class)
                .getResultList();
    }

    @Transactional
    public Sanction update(Sanction sanction) {
        return entityManager.merge(sanction);
    }

    @Transactional
    public void delete(Long id) {
        Sanction sanction = findById(id);
        if (sanction != null) {
            entityManager.remove(sanction);
        }
    }
}