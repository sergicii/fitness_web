package com.fitness.service;

import com.fitness.dao.ReservationDAO;
import com.fitness.enums.ReservationState;
import com.fitness.factory.DAOFactory;
import com.fitness.model.business.Reservation;

import java.util.List;
import java.util.Optional;

public class ReservationService {
    private final ReservationDAO reservationDAO = DAOFactory.getDAO(ReservationDAO.class);

    public boolean addReservation(Reservation reservation) {
        return reservationDAO.create(reservation);
    }

    public boolean updateReservation(Reservation reservation) {
        return reservationDAO.update(reservation);
    }

    public boolean updateReservationState(Long id, ReservationState state) {
        Reservation reservation = getReservation(id);
        if (reservation != null) {
            reservation.setState(state);
            return reservationDAO.update(reservation);
        }
        return false;
    }

    public boolean deleteReservation(Long id) {
        return reservationDAO.delete(id);
    }

    public Reservation getReservation(Long id) {
        return reservationDAO.findById(id).orElse(null);
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.findAll();
    }
}
