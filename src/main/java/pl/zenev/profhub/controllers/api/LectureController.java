package pl.zenev.profhub.controllers.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.zenev.profhub.dto.GetLectureResponse;
import pl.zenev.profhub.dto.GetLecturesResponse;
import pl.zenev.profhub.dto.PatchLectureRequest;
import pl.zenev.profhub.dto.PutLectureRequest;

import java.util.UUID;

@Path("")
public interface LectureController {

    @GET
    @Path("/courses/{id}/lectures")
    @Produces(MediaType.APPLICATION_JSON)
    GetLecturesResponse getCourseLectures(@PathParam("id") UUID id);

    @GET
    @Path("/courses/{id_course}/lectures/{id_lecture}")
    @Produces(MediaType.APPLICATION_JSON)
    GetLectureResponse getCourseLecture(@PathParam("id_course") UUID courseId, @PathParam("id_lecture") UUID lectureId);

    @PUT
    @Path("/courses/{id_course}/lectures/{id_lecture}")
    @Produces(MediaType.APPLICATION_JSON)
    void putLecture(@PathParam("id_course") UUID courseId, @PathParam("id_lecture") UUID lectureId, PutLectureRequest request);

    @PATCH
    @Path("/courses/{id_course}/lectures/{id_lecture}")
    @Produces(MediaType.APPLICATION_JSON)
    void patchLecture(@PathParam("id_course") UUID courseId, @PathParam("id_lecture") UUID lectureId, PatchLectureRequest request);

    @DELETE
    @Path("/courses/{id_course}/lectures/{id_lecture}")
    void deleteLecture(@PathParam("id_course") UUID courseId, @PathParam("id_lecture") UUID lectureId);
}