package com.fitness.controller;

import com.fitness.dao.UserDAO;
import com.fitness.enums.UserRol;
import com.fitness.factory.ServiceFactory;
import com.fitness.model.user.Client;
import com.fitness.model.user.Employee;
import com.fitness.model.user.User;
import com.fitness.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = ServiceFactory.getService(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        boolean fields = email == null || email.isBlank() || password == null || password.isBlank();

        if (fields) {
            req.setAttribute("error", "El correo y la contraseña son obligatorios.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        Optional<User> userOptional = userService.login(email, password);

        // 2. Verificamos si el Optional contiene un usuario
        if (userOptional.isEmpty()) {
            req.setAttribute("error", "Correo o contraseña incorrectos.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        if (userOptional.get().getRol().equals(UserRol.CLIENT)) {
            Optional<Client> client = userService.getClientFromUser(userOptional.get());
            HttpSession session = req.getSession();
            client.ifPresent(value -> session.setAttribute("loggedInUser", value));
            resp.sendRedirect(req.getContextPath() + "/client");
            return;
        }

        Optional<Employee> employee = userService.getEmployeeFromUser(userOptional.get());
        if (employee.isPresent()) {
            String targetUrl = switch (employee.get().getArea()) {
                case TRAINERS -> "/trainer";
                case OPERACIONES -> "/operation";
                case VENTAS -> "/commercial";
                default -> "/home";
            };

            HttpSession session = req.getSession();
            session.setAttribute("loggedInUser", employee.get());

            resp.sendRedirect(req.getContextPath() + targetUrl);
        }
    }
}
