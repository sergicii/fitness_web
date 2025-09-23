package com.fitness.controller;

import com.fitness.enums.ContractedState;
import com.fitness.enums.Gender;
import com.fitness.enums.UserRol;
import com.fitness.factory.ServiceFactory;
import com.fitness.model.service.Contracted;
import com.fitness.model.service.Membership;
import com.fitness.model.user.Client;
import com.fitness.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@WebServlet("/commercial")
public class CommercialServlet extends HttpServlet {
    private final MembershipService membershipService = ServiceFactory.getService(MembershipService.class);
    private final ClientService clientService = ServiceFactory.getService(ClientService.class);
    private final UserService userService = ServiceFactory.getService(UserService.class);
    private final IncidentService incidentService = ServiceFactory.getService(IncidentService.class);
    private final ContractedService contractedService = ServiceFactory.getService(ContractedService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("memberships", membershipService.getAllMembership());
        req.setAttribute("genders", Gender.values());
        req.setAttribute("clients", clientService.getAllClients());
        req.setAttribute("incidents", incidentService.getIncidents());
        req.setAttribute("contractedStates", ContractedState.values());
        req.setAttribute("contracted", contractedService.getAllContracted());
        req.getRequestDispatcher("/commercial_dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("createClient".equals(action)) {
            try {
                String dni = req.getParameter("dni");
                String firstname = req.getParameter("firstname");
                String lastname = req.getParameter("lastname");
                String numberPhone = req.getParameter("numberPhone");
                String address = req.getParameter("address");
                int age = Integer.parseInt(req.getParameter("age"));
                Gender gender = Gender.valueOf(req.getParameter("gender"));

                Client newClient = new Client();
                newClient.setFirstname(firstname);
                newClient.setLastname(lastname);
                newClient.setDni(dni);
                newClient.setNumberPhone(numberPhone);
                newClient.setAddress(address);
                newClient.setAge(age);
                newClient.setGender(gender);

                boolean success = clientService.addClient(newClient);

                if (success) {
                    req.getSession().setAttribute("message", "Cliente '" + firstname + "' creado con éxito.");
                } else {
                    req.getSession().setAttribute("error", "No se pudo crear el cliente. El DNI o Email ya podría existir.");
                }

            } catch (Exception e) {
                req.getSession().setAttribute("error", "Error procesando la solicitud: " + e.getMessage());
            }
        } else if ("contractMembership".equals(action)) {
            try {
                Long clientId = Long.parseLong(req.getParameter("clientId"));
                Long membershipId = Long.parseLong(req.getParameter("membershipId"));
                ContractedState state = ContractedState.valueOf(req.getParameter("state"));

                String email = req.getParameter("email");
                String password = req.getParameter("password");

                Optional<Client> clientOpt = clientService.getClientById(clientId);
                Optional<Membership> membershipOpt = membershipService.getMembership(membershipId);

                if (clientOpt.isPresent() && membershipOpt.isPresent()) {
                    Client client = clientOpt.get();
                    Membership membership = membershipOpt.get();

                    if (client.getUser() == null) {
                        client.setUser(userService.register(email, password, UserRol.CLIENT));
                        clientService.update(client);
                    }

                    Contracted newContract = new Contracted();
                    newContract.setClient(client);
                    newContract.setMembership(membership);
                    newContract.setStart(LocalDate.now());
                    newContract.setEnd(newContract.getStart().plusDays(membership.getDuration().toDays()));
                    newContract.setState(state);

                    boolean success = contractedService.addContracted(newContract);

                    if (success) {
                        req.getSession().setAttribute("message", "Membresía contratada con éxito para " + client.getFirstname() + ". Se ha creado o verificado su cuenta de usuario.");
                    } else {
                        req.getSession().setAttribute("error", "No se pudo registrar la contratación. El email de usuario ya podría existir.");
                    }
                } else {
                    req.getSession().setAttribute("error", "El cliente o la membresía seleccionada no existen.");
                }
            } catch (Exception e) {
                req.getSession().setAttribute("error", "Error procesando la solicitud: " + e.getMessage());
            }
        }
        resp.sendRedirect(req.getContextPath() + "/commercial");
    }
}
