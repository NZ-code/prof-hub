package pl.zenev.profhub.controllers.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.zenev.profhub.dto.GetCourseResponse;
import pl.zenev.profhub.dto.GetCoursesResponse;
import pl.zenev.profhub.dto.PutCourseRequest;

import java.util.UUID;

@Path("")
public interface CourseController {
    @GET
    @Path("/courses")
    @Produces(MediaType.APPLICATION_JSON)
    GetCoursesResponse getCourses();

    @GET
    @Path("/courses/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetCourseResponse getCourse(@PathParam("id") UUID id);

    @PUT
    @Path("/courses/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void putCourse(@PathParam("id") UUID id, PutCourseRequest request);

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
