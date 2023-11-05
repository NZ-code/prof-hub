package pl.zenev.profhub.controllers.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import pl.zenev.profhub.controllers.api.CourseController;
import pl.zenev.profhub.dto.GetCoursesResponse;
import pl.zenev.profhub.helpers.CoursesToResponse;
import pl.zenev.profhub.services.CourseService;

@Path("")
public class CourseRestController implements CourseController {
    private HttpServletResponse response;
    private final CourseService courseService;
    private final UriInfo uriInfo;
    private final CoursesToResponse coursesToResponse;
    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public CourseRestController(CourseService courseService, UriInfo uriInfo, CoursesToResponse coursesToResponse) {
        this.courseService = courseService;
        this.uriInfo = uriInfo;
        this.coursesToResponse = coursesToResponse;
    }

    @Override
    public GetCoursesResponse getCourses() {
        return coursesToResponse.apply(courseService.getAll());
    }
}
