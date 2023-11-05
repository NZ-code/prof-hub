package pl.zenev.profhub.helpers;

import jakarta.enterprise.context.ApplicationScoped;
import pl.zenev.profhub.models.CourseModel;
import pl.zenev.profhub.entities.Course;

import java.util.function.Function;

@ApplicationScoped
public class CourseToModel implements Function<Course, CourseModel> {
    @Override
    public CourseModel apply(Course entity) {
        return CourseModel.builder()
                .uuid(entity.getUuid())
                .name(entity.getName())
                .startDate(entity.getStartDate()).build();
    }
}