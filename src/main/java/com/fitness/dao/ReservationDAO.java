package com.fitness.dao;

import com.fitness.model.business.Reservation;
import com.fitness.util.JPAInitializer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ReservationDAO {
    private final EntityManagerFactory emf;
    private static final Logger logger = LoggerFactory.getLogger(ReservationDAO.class);

    public ReservationDAO() {
        this.emf = JPAInitializer.getEntityManagerFactory();
    }

    /**
     * Persiste una nueva reservación en la base de datos.
     * @param reservation La entidad Reservation a guardar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public boolean create(Reservation reservation) {
        return JPAInitializer.executeInsideTransaction(em -> em.persist(reservation));
    }

    /**
     * Actualiza una reservación existente en la base de datos.
     * @param reservation La entidad Reservation con los datos actualizados.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public boolean update(Reservation reservation) {
        return JPAInitializer.executeInsideTransaction(em -> em.merge(reservation));
    }

    /**
     * Elimina una reservación de la base de datos por su ID.
     * @param reservationId El ID de la reservación a eliminar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public boolean delete(Long reservationId) {
        return JPAInitializer.executeInsideTransaction(em -> {
            Reservation reservation = em.find(Reservation.class, reservationId);
            if (reservation != null) {
                em.remove(reservation);
            }
        });
    }

    /**
     * Busca una reservación por su ID.
     * @param id El ID de la reservación a buscar.
     * @return Un Optional que contiene la reservación si se encuentra, o un Optional vacío.
     */
    public Optional<Reservation> findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Reservation reservation = em.find(Reservation.class, id);
            return Optional.ofNullable(reservation);
        } catch (Exception e) {
            logger.error("Error al buscar reservación por ID: {}", id, e);
            return Optional.empty();
        }
    }

    /**
     * Obtiene todas las reservaciones de la base de datos.
     * @return Una lista con todas las reservaciones.
     */
    public List<Reservation> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT r FROM Reservation r";
            TypedQuery<Reservation> query = em.createQuery(jpql, Reservation.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error al obtener todas las reservaciones.", e);
            return Collections.emptyList();
        }
    }
}
