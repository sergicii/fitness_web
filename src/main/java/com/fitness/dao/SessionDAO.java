package com.fitness.dao;

import com.fitness.model.business.Session;
import com.fitness.util.JPAInitializer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SessionDAO {
    private final EntityManagerFactory emf;
    private static final Logger logger = LoggerFactory.getLogger(SessionDAO.class);

    public SessionDAO() {
        emf = JPAInitializer.getEntityManagerFactory();
    }

    public boolean create(Session session) {
        return JPAInitializer.executeInsideTransaction(em -> em.persist(session));
    }

    public boolean update(Session session) {
        return JPAInitializer.executeInsideTransaction(em -> em.merge(session));
    }

    public boolean delete(Long sessionId) { // Cambiado para aceptar un ID
        return JPAInitializer.executeInsideTransaction(em -> {
            Session sessionToDelete = em.find(Session.class, sessionId);

            if (sessionToDelete != null) {
                em.remove(sessionToDelete);
            } else {
                logger.warn("Se intentó eliminar una sesión con ID {} que no existe.", sessionId);
            }
        });
    }

    public boolean isTrainerBusy(Long trainerId, LocalDateTime start, LocalDateTime end) {
        try (EntityManager em = emf.createEntityManager()) {
            Long count = em.createQuery(
                            "SELECT COUNT(s) FROM Session s " +
                                    "WHERE s.trainer.id = :trainerId " +
                                    "AND s.start < :end AND s.end > :start", Long.class)
                    .setParameter("trainerId", trainerId)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            logger.error("Error al verificar si el entrenador está ocupado.", e);
            return false;
        }
    }

    public int getConcurrentParticipantsInSite(Long siteId, LocalDateTime start, LocalDateTime end) {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT SUM(s.maxParticipants) FROM Session s " +
                    "WHERE s.site.id = :siteId " +
                    "AND s.start < :end AND s.end > :start";

            Long total = em.createQuery(jpql, Long.class)
                    .setParameter("siteId", siteId)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getSingleResult();

            return total != null ? total.intValue() : 0;
        } catch (Exception e) {
            return 0;
        }
    }


    public Optional<Session> findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Session session = em.find(Session.class, id);
            return Optional.ofNullable(session);
        } catch (Exception e) {
            logger.error("Error al buscar la sesión por ID: {}", id, e);
            return Optional.empty();
        }
    }

    public List<Session> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT s FROM Session s";
            TypedQuery<Session> query = em.createQuery(jpql, Session.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todos los lugares.", e);
            return Collections.emptyList();
        }
    }
}
