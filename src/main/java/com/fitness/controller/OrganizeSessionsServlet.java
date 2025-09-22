package com.fitness.controller;

import com.fitness.factory.ServiceFactory;
import com.fitness.service.SiteService;
import com.fitness.service.TrainingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/organize_session")
public class OrganizeSessionsServlet extends HttpServlet {
    private final TrainingService trainingService = ServiceFactory.getService(TrainingService.class);
    private final SiteService siteService = ServiceFactory.getService(SiteService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("sites", siteService.getAllSites());
        req.setAttribute("trainings", trainingService.getAllTrainings());

        req.getRequestDispatcher("/organize_sessions.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
