package pl.zenev.profhub.helpers;

import pl.zenev.profhub.dto.PatchCourseRequest;
import pl.zenev.profhub.entities.Course;

import java.util.function.BiFunction;

public class PatchRequestToCourse implements BiFunction<Course, PatchCourseRequest, Course>  {
    @Override
    public Course apply(Course course, PatchCourseRequest patchCourseRequest) {
        return Course
                .builder()
                .uuid(course.getUuid())
                .name(patchCourseRequest.getName())
                .build();
    }
}
