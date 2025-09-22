package com.fitness.dao;

import com.fitness.model.service.Membership;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

public class MembershipDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Membership membership) {
        entityManager.persist(membership);
    }

    public Membership findById(Long id) {
        return entityManager.find(Membership.class, id);
    }

    public List<Membership> findAll() {
        return entityManager.createQuery("SELECT m FROM Membership m", Membership.class)
                .getResultList();
    }

    @Transactional
    public Membership update(Membership membership) {
        return entityManager.merge(membership);
    }

    @Transactional
    public void delete(Long id) {
        Membership membership = findById(id);
        if (membership != null) {
            entityManager.remove(membership);
        }
    }
}