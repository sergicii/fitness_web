package com.fitness.controller;

import com.fitness.enums.ReservationState;
import com.fitness.factory.ServiceFactory;
import com.fitness.model.business.Session;
import com.fitness.model.user.Employee;
import com.fitness.service.ReservationService;
import com.fitness.service.SessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/trainer")
public class TrainerServlet extends HttpServlet {
    private final SessionService sessionService = ServiceFactory.getService(SessionService.class);
    private final ReservationService reservationService = ServiceFactory.getService(ReservationService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("loggedInUser") == null || !(session.getAttribute("loggedInUser") instanceof Employee trainer)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<Session> trainerSessions = sessionService.getAllSession().stream()
                .filter(s -> s.getTrainer() != null && s.getTrainer().getId().equals(trainer.getId()))
                .toList();

        req.setAttribute("sessions", trainerSessions);
        req.setAttribute("reservationStates", ReservationState.values());
        req.getRequestDispatcher("/trainer_dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        // 3. Lógica para procesar la actualización del formulario
        if ("updateSessionDetails".equals(action)) {
            try {
                long sessionId = Long.parseLong(req.getParameter("sessionId"));
                String sessionReport = req.getParameter("sessionReport");

                sessionService.updateReport(sessionId, sessionReport);

                // 5. Iteramos sobre todos los parámetros de la petición para encontrar las reservas
                for (String paramName : req.getParameterMap().keySet()) {
                    // Buscamos parámetros que empiecen con "reservationStatus_"
                    if (paramName.startsWith("reservationStatus_")) {
                        // Extraemos el ID de la reserva del nombre del parámetro
                        long reservationId = Long.parseLong(paramName.substring("reservationStatus_".length()));
                        // Obtenemos el nuevo estado seleccionado en el <select>
                        ReservationState newState = ReservationState.valueOf(req.getParameter(paramName));

                        // Actualizamos el estado de esa reserva específica
                        reservationService.updateReservationState(reservationId, newState);
                    }
                }
                req.getSession().setAttribute("message", "Sesión ID " + sessionId + " actualizada correctamente.");
            } catch (Exception e) {
                req.getSession().setAttribute("error", "Error al actualizar la sesión: " + e.getMessage());
            }
        }
        // 6. Redirigimos de vuelta al panel del entrenador para ver los cambios
        resp.sendRedirect(req.getContextPath() + "/trainer");
    }
}
