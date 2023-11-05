package pl.zenev.profhub.helpers;

import jakarta.enterprise.context.ApplicationScoped;
import pl.zenev.profhub.dto.GetCourseResponse;
import pl.zenev.profhub.entities.Course;

import java.util.function.Function;

@ApplicationScoped
public class CourseToResponse implements Function<Course, GetCourseResponse> {
    @Override
    public GetCourseResponse apply(Course course) {
        return GetCourseResponse
                .builder()
                .id(course.getUuid())
                .name(course.getName())
                .build();
    }
}
