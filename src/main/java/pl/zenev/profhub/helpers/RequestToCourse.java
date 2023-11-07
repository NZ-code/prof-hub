package pl.zenev.profhub.helpers;

import jakarta.enterprise.context.ApplicationScoped;
import pl.zenev.profhub.dto.PutCourseRequest;
import pl.zenev.profhub.entities.Course;

import java.util.UUID;
import java.util.function.BiFunction;

@ApplicationScoped
public class RequestToCourse implements BiFunction<UUID, PutCourseRequest, Course> {
    @Override
    public Course apply(UUID uuid, PutCourseRequest putCourseRequest) {
        return Course
                .builder()
                .uuid(uuid)
                .name(putCourseRequest.getName())
                .startDate(putCourseRequest.getStartDate())
                .build();
    }
}
