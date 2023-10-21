package pl.zenev.profhub.controllers;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;
import pl.zenev.profhub.services.ProfessorService;

//@WebListener
//public class CreateControllers implements ServletContextListener {
//    @Override
//    public void contextInitialized(ServletContextEvent event) {
//        ProfessorService professorService = (ProfessorService) event.getServletContext().getAttribute("professorService");
//        event.getServletContext().setAttribute("professorController", new ProfessorsController(professorService));
//    }
//}
