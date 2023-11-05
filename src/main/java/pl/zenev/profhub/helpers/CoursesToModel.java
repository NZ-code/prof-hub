package pl.zenev.profhub.helpers;

import jakarta.enterprise.context.ApplicationScoped;
import models.CoursesModel;
import pl.zenev.profhub.entities.Course;

import java.util.List;
import java.util.function.Function;

@ApplicationScoped
public class CoursesToModel implements Function<List<Course>, CoursesModel> {

    @Override
    public CoursesModel apply(List<Course> courses) {
        return CoursesModel.
                builder()
                .courses(courses.stream()
                        .map(course -> CoursesModel.Course.builder()
                                .id(course.getUuid())
                                .name(course.getName())
                                .build())
                        .toList()).
                build();
    }
}
