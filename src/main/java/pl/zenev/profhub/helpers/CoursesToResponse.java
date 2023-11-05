package pl.zenev.profhub.helpers;

import jakarta.enterprise.context.ApplicationScoped;
import pl.zenev.profhub.dto.GetCoursesResponse;
import pl.zenev.profhub.entities.Course;

import java.util.List;
import java.util.function.Function;

@ApplicationScoped
public class CoursesToResponse implements Function<List<Course>, GetCoursesResponse> {

    @Override
    public GetCoursesResponse apply(List<Course> entities) {
        return GetCoursesResponse.builder()
                .courses(
                        entities.stream()
                                .map(course -> GetCoursesResponse.Course.builder()
                                        .id(course.getUuid())
                                        .name(course.getName())
                                        .build()).toList())
                .build();
    }
}
