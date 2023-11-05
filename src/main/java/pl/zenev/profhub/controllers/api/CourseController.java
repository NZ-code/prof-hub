package pl.zenev.profhub.controllers.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.zenev.profhub.dto.GetCoursesResponse;

import java.util.UUID;

@Path("")
public interface CourseController {
    @GET
    @Path("/courses")
    @Produces(MediaType.APPLICATION_JSON)
    GetCoursesResponse getCourses();

//    @GET
//    @Path("/products/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    GetProductResponse getProduct(@PathParam("id") UUID id);
//
//    @PUT
//    @Path("/products/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    void putProduct(@PathParam("id") UUID id, PutProductRequest request);
//
//    @DELETE
//    @Path("/products/{id}")
//    void deleteProduct(@PathParam("id") UUID id);
//
//    @PATCH
//    @Path("/products/{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    void patchProduct(@PathParam("id") UUID id, PatchProductRequest request);
}
