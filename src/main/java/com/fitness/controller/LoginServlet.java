package com.fitness.controller;

import com.fitness.dao.UserDAO;
import com.fitness.enums.UserRol;
import com.fitness.factory.ServiceFactory;
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

        UserRol rol = userService.login(email, password);

        if (rol == null) {
            req.setAttribute("error", "Correo o contraseña incorrectos.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("loggedInUser", rol.toString());
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
