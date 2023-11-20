package pl.zenev.profhub.controllers.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.zenev.profhub.dto.*;

import java.util.UUID;

@Path("")
public interface ProfessorsController {
    @GET
    @Path("/professors")
    @Produces(MediaType.APPLICATION_JSON)
    GetProfessorsResponse getProfessors();

    @GET
    @Path("/professors/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetProfessorResponse getProfessor(@PathParam("id") UUID id);

    @PUT
    @Path("/professors/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void putProfessor(@PathParam("id") UUID id, PutProfessorRequest request);

    @DELETE
    @Path("/professors/{id}")
    void deleteProfessor(@PathParam("id") UUID id);

    @PATCH
    @Path("/professors/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchProfessor(@PathParam("id") UUID id, PatchProfessorRequest request);
}