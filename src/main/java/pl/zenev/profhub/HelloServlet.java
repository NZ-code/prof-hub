package pl.zenev.profhub;

import java.io.*;
import java.util.List;
import java.util.regex.Pattern;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pl.zenev.profhub.controllers.ProfessorsController;
import pl.zenev.profhub.dto.GetProfessorResponse;
import pl.zenev.profhub.dto.GetProfessorsResponse;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "helloServlet", value = "/api/*")
public class HelloServlet extends HttpServlet {
    private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
    public static final Pattern PROFESSORS_PATTERN = Pattern.compile("/professors/");
    public static final Pattern PROFESSOR_PATTERN = Pattern.compile("/professors/(%s)/".formatted(UUID.pattern()));
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
        String path = parseRequestPath(request);
        //  getProfessorsResponse
        PrintWriter out = response.getWriter();
        if(path.matches(PROFESSORS_PATTERN.pattern())){
            GetProfessorsResponse getProfessorsResponse = professorsController.getAllProfessors();
            out.write(jsonb.toJson(getProfessorsResponse));
        }
        if(path.matches(PROFESSOR_PATTERN.pattern())){
            UUID uuid = extractUuid(PROFESSOR_PATTERN, path);
            GetProfessorResponse getProfessorResponse = professorsController.getProfessorById(uuid);
            out.write(jsonb.toJson(getProfessorResponse));
        }



        // getProfessorById
    }
    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }
    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return java.util.UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    public void destroy() {
    }
}