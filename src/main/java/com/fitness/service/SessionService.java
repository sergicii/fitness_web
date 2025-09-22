package com.fitness.service;

import com.fitness.dao.SessionDAO;
import com.fitness.factory.DAOFactory;
import com.fitness.model.business.Session;
import com.fitness.model.business.Site;
import com.fitness.model.business.Training;
import com.fitness.model.user.Employee;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SessionService {
    private final SessionDAO sessionDAO = DAOFactory.getDAO(SessionDAO.class);

    public boolean addSession(Session session, Employee trainer, Site site, Training training) {
        Session verificateSession = createSession(session, trainer, site, training);
        if (verificateSession == null) return false;
        return sessionDAO.create(verificateSession);
    }

    public boolean updateSession(Session session, Employee trainer, Site site, Training training) {
        Session verificateSession = createSession(session, trainer, site, training);
        if (verificateSession == null) return false;
        return sessionDAO.update(verificateSession);
    }

    private Session createSession(Session session, Employee trainer, Site site, Training training) {
        LocalDateTime start = session.getStart();
        LocalDateTime end = start.plus(training.getDuration());
        session.setEnd(end);

        boolean trainerBusy = sessionDAO.isTrainerBusy(trainer.getId(), start, end);

        if (trainerBusy) {
            System.out.println("Validación falló: Entrenador ocupado.");
            return null;
        }

        int requestedParticipants = session.getMaxParticipants();
        int currentParticipantsInSite = currentParticipants(site, start, end);

        if (requestedParticipants > training.getMaxParticipants()) {
            System.out.println("Validación falló: Participantes solicitados (" + requestedParticipants + ") exceden el máximo del entrenamiento (" + training.getMaxParticipants() + ").");
            return null;
        }

        if ((currentParticipantsInSite + requestedParticipants) > site.getCapacity()) {
            System.out.println("Validación falló: Capacidad del sitio excedida. Actuales: " + currentParticipantsInSite + ", Solicitados: " + requestedParticipants + ", Capacidad: " + site.getCapacity());
            return null;
        }

        session.setSite(site);
        session.setTrainer(trainer);
        session.setTraining(training);
        return session;
    }

    public int currentParticipants(Site site, LocalDateTime start, LocalDateTime end) {
        return sessionDAO.getConcurrentParticipantsInSite(site.getId(), start, end);
    }

    public Optional<Session> getSession(long id) {
        return sessionDAO.findById(id);
    }

    public boolean deleteSession(Long id) {
        return sessionDAO.delete(id);
    }

    public List<Session> getAllSession() {
        List<Session> sessions = sessionDAO.findAll();
        sessions.sort(Comparator.comparing(Session::getStart).reversed());
        return sessions;
    }
}
