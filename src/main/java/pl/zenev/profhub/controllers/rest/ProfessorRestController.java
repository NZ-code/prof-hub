package pl.zenev.profhub.controllers.rest;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import pl.zenev.profhub.controllers.api.CourseController;
import pl.zenev.profhub.controllers.api.ProfessorsController;
import pl.zenev.profhub.dto.*;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.entities.Professor;
import pl.zenev.profhub.helpers.*;
import pl.zenev.profhub.security.UserRoles;
import pl.zenev.profhub.services.CourseService;
import pl.zenev.profhub.services.ProfessorService;

import java.util.UUID;

import static pl.zenev.profhub.security.UserRoles.ADMIN;

@Path("")
@Log
public class ProfessorRestController implements ProfessorsController {
    private HttpServletResponse response;
    private ProfessorService professorService;
    private final UriInfo uriInfo;
    private final ProfessorsToResponse professorsToResponse;
    private final ProfessorToResponse professorToResponse;
    private final RequestToProfessor requestToProfessor;
    private final PatchRequestToProfessor patchRequestToProfessor;
    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public ProfessorRestController(UriInfo uriInfo,
                                   ProfessorsToResponse professorsToResponse,
                                   ProfessorToResponse professorToResponse,
                                   RequestToProfessor requestToProfessor,
                                   PatchRequestToProfessor patchRequestToProfessor) {
        this.uriInfo = uriInfo;
        this.professorsToResponse = professorsToResponse;
        this.professorToResponse = professorToResponse;
        this.requestToProfessor = requestToProfessor;
        this.patchRequestToProfessor = patchRequestToProfessor;
    }
    @EJB
    public void setService(ProfessorService professorService){
        this.professorService = professorService;
    }
    @Override
    public GetProfessorsResponse getProfessors() {
        return professorsToResponse.apply(professorService.getAll());
    }

    @RolesAllowed(ADMIN)
    @Override
    public GetProfessorResponse getProfessor(UUID id) {
        return professorService.getById(id).map(
                professorToResponse
        ).orElseThrow(NotFoundException::new);
    }

    @RolesAllowed(ADMIN)
    @Override
    public void putProfessor(UUID id, PutProfessorRequest request) {
        try{
            System.out.println("UUID:" + id);
            professorService.add(requestToProfessor.apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ProfessorsController.class, "getProfessor")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        }
        catch (IllegalArgumentException ex) {
            System.out.println(ex);
            throw new BadRequestException(ex);
        }
    }

    @Override
    @RolesAllowed(ADMIN)
    public void deleteProfessor(UUID id) {
        Professor professor = professorService.getById(id).orElseThrow(NotFoundException::new);
        professorService.delete(professor);
    }

    @Override
    @RolesAllowed(ADMIN)
    public void patchProfessor(UUID id, PatchProfessorRequest request) {
        professorService.getById(id).ifPresentOrElse(
                entity ->  professorService.update(patchRequestToProfessor.apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
