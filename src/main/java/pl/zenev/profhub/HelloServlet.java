package pl.zenev.profhub;

import java.io.*;
import java.util.List;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pl.zenev.profhub.controllers.ProfessorsController;
import pl.zenev.profhub.dto.GetProfessorsResponse;
import pl.zenev.profhub.models.Professor;
import pl.zenev.profhub.repositories.ProfessorRepository;
import pl.zenev.profhub.services.ProfessorService;

@WebServlet(name = "helloServlet", value = "/api")
public class HelloServlet extends HttpServlet {
    private final Jsonb jsonb = JsonbBuilder.create();
    private ProfessorsController professorsController;
    private String message;

    public void init() throws ServletException {
        super.init();
        professorsController = (ProfessorsController) getServletContext().getAttribute("professorController");
        message = "Helo World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        // Hello
        PrintWriter out = response.getWriter();
        GetProfessorsResponse getProfessorsResponse = professorsController.getAllProfessors();
        out.write(jsonb.toJson(getProfessorsResponse));
    }

    public void destroy() {
    }
}