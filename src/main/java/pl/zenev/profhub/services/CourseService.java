package pl.zenev.profhub.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.models.Course;
import pl.zenev.profhub.repositories.CourseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ApplicationScoped
@NoArgsConstructor(force = true)
public class CourseService implements Service<Course>{
    CourseRepository courseRepository;
    @Inject
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getAll() {
        return null;
    }

    @Override
    public Optional<Course> getById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public void add(Course course) {
        courseRepository.add(course);
    }
}
