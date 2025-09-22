package com.fitness.controller;

import com.fitness.enums.SessionType;
import com.fitness.factory.ServiceFactory;
import com.fitness.model.business.Session;
import com.fitness.model.business.Site;
import com.fitness.model.business.Training;
import com.fitness.model.user.Employee;
import com.fitness.service.EmployeeService;
import com.fitness.service.SessionService;
import com.fitness.service.SiteService;
import com.fitness.service.TrainingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@WebServlet("/organize_session")
public class OrganizeSessionsServlet extends HttpServlet {
    private final TrainingService trainingService = ServiceFactory.getService(TrainingService.class);
    private final SiteService siteService = ServiceFactory.getService(SiteService.class);
    private final EmployeeService employeeService = ServiceFactory.getService(EmployeeService.class);
    private final SessionService sessionService = ServiceFactory.getService(SessionService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("sites", siteService.getAllSites());
        req.setAttribute("trainings", trainingService.getAllTrainings());
        req.setAttribute("trainers", employeeService.getAllTrainers());
        req.setAttribute("sessions", sessionService.getAllSession());
        req.setAttribute("sessionTypes", SessionType.values());
        req.getRequestDispatcher("/organize_sessions.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if ("create".equals(action)) {
                Long siteId = Long.parseLong(req.getParameter("siteId"));
                long trainingId = Long.parseLong(req.getParameter("trainingId"));
                Long trainerId = Long.parseLong(req.getParameter("trainerId"));
                SessionType sessionType = SessionType.valueOf(req.getParameter("sessionType"));
                LocalDateTime start = LocalDateTime.parse(req.getParameter("start"));

                int maxParticipants = Integer.parseInt(req.getParameter("maxParticipants"));

                Optional<Site> siteOpt = siteService.getSite(siteId);
                Optional<Training> trainingOpt = trainingService.getTraining(trainingId);
                Optional<Employee> trainerOpt = employeeService.getTrainer(trainerId);

                if (start.isAfter(LocalDateTime.now()) && siteOpt.isPresent()
                        && trainingOpt.isPresent() && trainerOpt.isPresent()) {
                    Session newSession = new Session();
                    newSession.setStart(start);
                    newSession.setType(sessionType);
                    newSession.setMaxParticipants(maxParticipants);

                    if (newSession.getType().equals(SessionType.PERSONALIZADA)) {
                        newSession.setMaxParticipants(1);
                    }

                    boolean success = sessionService.addSession(newSession, trainerOpt.get(), siteOpt.get(), trainingOpt.get());

                    if (success) {
                        req.getSession().setAttribute("message", "¡Sesión creada con éxito!");
                    } else {
                        req.getSession().setAttribute("error", "No se pudo crear la sesión. Conflicto de horario o capacidad excedida.");
                    }
                } else {
                    req.getSession().setAttribute("error", "Los datos de instalación, entrenamiento, fecha y hora de inicio o entrenador son inválidos.");
                }

            } else if ("delete".equals(action)) {
                long sessionId = Long.parseLong(req.getParameter("sessionId"));
                boolean success = sessionService.deleteSession(sessionId);

                if (success) {
                    req.getSession().setAttribute("message", "Sesión ID " + sessionId + " eliminada correctamente.");
                } else {
                    req.getSession().setAttribute("error", "No se pudo eliminar la sesión ID " + sessionId + ".");
                }
            }
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Error procesando la solicitud: " + e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/organize_session");
    }
}
