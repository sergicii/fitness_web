package com.fitness.controller;

import com.fitness.enums.IncidentState;
import com.fitness.enums.ReservationState;
import com.fitness.factory.ServiceFactory;
import com.fitness.model.business.Reservation;
import com.fitness.model.business.Session;
import com.fitness.model.monitoring.Incident;
import com.fitness.model.user.Client;
import com.fitness.model.user.Employee;
import com.fitness.service.IncidentService;
import com.fitness.service.ReservationService;
import com.fitness.service.SessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@WebServlet("/client")
public class ClientServlet extends HttpServlet {
    private final SessionService sessionService = ServiceFactory.getService(SessionService.class);
    private final ReservationService reservationService = ServiceFactory.getService(ReservationService.class);
    private final IncidentService incidentService = ServiceFactory.getService(IncidentService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("loggedInUser") == null || !(session.getAttribute("loggedInUser") instanceof Client client)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<Incident> incidents = incidentService.getIncidents()
                .stream()
                .filter(i -> i.getClient().getId().equals(client.getId()))
                .toList();

        List<Reservation> reservations = reservationService.getAllReservations()
                .stream()
                .filter(r -> r.getClient().getId().equals(client.getId()))
                .toList();

        req.setAttribute("sessions", sessionService.getAllSession());
        req.setAttribute("reservations", reservations);
        req.setAttribute("incidents", incidents);
        req.getRequestDispatcher("/client_dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession httpSession = req.getSession(false);

        if (httpSession == null || httpSession.getAttribute("loggedInUser") == null || !(httpSession.getAttribute("loggedInUser") instanceof Client client)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if ("reserva".equals(action)) {

            try {
                // Obtenemos el ID de la sesión desde el formulario
                long sessionId = Long.parseLong(req.getParameter("sessionId"));

                // Buscamos la sesión completa en la base de datos
                Optional<Session> sessionOpt = sessionService.getSession(sessionId);

                if (sessionOpt.isPresent()) {
                    Session sessionToReserve = sessionOpt.get();

                    // Creamos el nuevo objeto de reservación
                    Reservation newReservation = new Reservation();
                    newReservation.setClient(client);
                    newReservation.setSession(sessionToReserve);

                    // Guardamos la reservación usando el servicio
                    boolean success = reservationService.addReservation(newReservation);

                    if (success) {
                        httpSession.setAttribute("message", "¡Reserva para la sesión " + sessionId + " realizada con éxito!");
                    } else {
                        httpSession.setAttribute("error", "No se pudo realizar la reserva. Inténtalo de nuevo.");
                    }
                } else {
                    httpSession.setAttribute("error", "La sesión que intentas reservar ya no existe.");
                }
            } catch (Exception e) {
                httpSession.setAttribute("error", "Ocurrió un error al procesar tu reserva: " + e.getMessage());
            }
        } else if ("createIncident".equals(action)) {
            try {
                // Obtenemos los parámetros del formulario de reclamo
                String title = req.getParameter("title");
                String description = req.getParameter("description");

                // Creamos el nuevo objeto Incident
                Incident newIncident = new Incident();
                newIncident.setTitle(title);
                newIncident.setDescription(description);
                newIncident.setClient(client); // Asignamos el cliente logueado
                newIncident.setDate(LocalDateTime.now()); // Asignamos la fecha y hora actual
                newIncident.setState(IncidentState.SIN_REVISAR); // Estado inicial "Sin Revisar"

                // Guardamos el reclamo usando el servicio
                boolean success = incidentService.addIncident(newIncident);

                if (success) {
                    httpSession.setAttribute("message", "Reclamo enviado con éxito. Será revisado pronto.");
                } else {
                    httpSession.setAttribute("error", "No se pudo enviar el reclamo. Por favor, inténtalo de nuevo.");
                }

            } catch (Exception e) {
                httpSession.setAttribute("error", "Ocurrió un error al procesar tu reclamo: " + e.getMessage());
            }
        }

        // 4. Redirigimos de vuelta al panel del cliente para ver el mensaje y la lista actualizada
        resp.sendRedirect(req.getContextPath() + "/client");
    }
}
