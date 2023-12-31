package pl.zenev.profhub;

import java.io.*;
import java.util.List;
import java.util.regex.Pattern;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.ws.rs.NotFoundException;
import pl.zenev.profhub.controllers.ProfessorsController;
import pl.zenev.profhub.dto.GetProfessorResponse;
import pl.zenev.profhub.dto.GetProfessorsResponse;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@MultipartConfig(maxFileSize = 200 * 1024)
@WebServlet(name = "helloServlet", value = "/api/*")
public class HelloServlet extends HttpServlet {
    private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
    public static final Pattern PROFESSORS_PATTERN = Pattern.compile("/professors");
    public static final Pattern PROFESSOR_PATTERN = Pattern.compile("/professors/(%s)".formatted(UUID.pattern()));
    public static final Pattern PROFESSOR_IMAGE_PATTERN = Pattern.compile("/professors/(%s)/image".formatted(UUID.pattern()));
    private final Jsonb jsonb = JsonbBuilder.create();
    private String message;
    private ProfessorsController professorsController;
    @Inject
    public HelloServlet(ProfessorsController professorsController) {
        this.professorsController = professorsController;
    }

//    public void init() throws ServletException {
//        super.init();
//        professorsController = (ProfessorsController) getServletContext().getAttribute("professorController");
//        message = "Helo World!";
//    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String path = parseRequestPath(request);
        if (path.matches(PROFESSOR_IMAGE_PATTERN.pattern())) {
            UUID uuid = extractUuid(PROFESSOR_IMAGE_PATTERN, path);
            professorsController.deleteImage(uuid);
        }
    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        if (path.matches(PROFESSOR_IMAGE_PATTERN.pattern())) {
            System.out.println("Image matches the pattern");
            UUID uuid = extractUuid(PROFESSOR_IMAGE_PATTERN, path);
            System.out.println("UUID extracted");
            professorsController.putImage(uuid, request.getPart("image").getInputStream() );

        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String path = parseRequestPath(request);
        //  getProfessorsResponse


        if(path.matches(PROFESSORS_PATTERN.pattern())){
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            GetProfessorsResponse getProfessorsResponse = professorsController.getAllProfessors();
            out.write(jsonb.toJson(getProfessorsResponse));
        }
        else if(path.matches(PROFESSOR_PATTERN.pattern())){
            UUID uuid = extractUuid(PROFESSOR_PATTERN, path);
            GetProfessorResponse getProfessorResponse = professorsController.getProfessorById(response, uuid);
            PrintWriter out = response.getWriter();
            if(getProfessorResponse == null){

                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            else{
                response.setContentType("application/json");
                out.write(jsonb.toJson(getProfessorResponse));
            }






        }
        else if(path.matches(PROFESSOR_IMAGE_PATTERN.pattern())){
            response.setContentType("image/png");
            UUID uuid = extractUuid(PROFESSOR_IMAGE_PATTERN, path);
            byte[] image = professorsController.getImage(uuid);
            response.setContentLength(image.length);
            response.getOutputStream().write(image);

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