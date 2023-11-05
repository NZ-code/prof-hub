package pl.zenev.profhub.controllers.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pl.zenev.profhub.controllers.api.CourseController;
import pl.zenev.profhub.dto.GetCourseResponse;
import pl.zenev.profhub.dto.GetCoursesResponse;
import pl.zenev.profhub.dto.PutCourseRequest;
import pl.zenev.profhub.dto.RequestToCourse;
import pl.zenev.profhub.helpers.CourseToResponse;
import pl.zenev.profhub.helpers.CoursesToResponse;
import pl.zenev.profhub.services.CourseService;

import java.util.UUID;

@Path("")
public class CourseRestController implements CourseController {
    private HttpServletResponse response;
    private final CourseService courseService;
    private final UriInfo uriInfo;
    private final CoursesToResponse coursesToResponse;
    private final CourseToResponse courseToResponse;
    private final RequestToCourse requestToCourse;
    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public CourseRestController(CourseService courseService, UriInfo uriInfo, CoursesToResponse coursesToResponse, CourseToResponse courseToResponse, RequestToCourse requestToCourse) {
        this.courseService = courseService;
        this.uriInfo = uriInfo;
        this.coursesToResponse = coursesToResponse;
        this.courseToResponse = courseToResponse;
        this.requestToCourse = requestToCourse;
    }

    @Override
    public GetCoursesResponse getCourses() {
        return coursesToResponse.apply(courseService.getAll());
    }

    @Override
    public GetCourseResponse getCourse(UUID id) {
        return courseService.getById(id).map(
                courseToResponse
        ).orElseThrow(NotFoundException::new);
    }

    @Override
    public void putCourse(UUID id, PutCourseRequest request) {
        try{
            System.out.println("UUID:" + id);
            courseService.add(requestToCourse.apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(CourseController.class, "getCourse")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        }
        catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }
}
