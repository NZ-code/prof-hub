package pl.zenev.profhub.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.models.Course;
import pl.zenev.profhub.models.Lecture;
import pl.zenev.profhub.repositories.CourseRepository;
import pl.zenev.profhub.repositories.LectureRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ApplicationScoped
@NoArgsConstructor(force = true)
public class CourseService implements Service<Course>{
    CourseRepository courseRepository;
    LectureRepository lectureRepository;
    @Inject
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Lecture> getCourseLectures(Course course){
        return lectureRepository.getLecturesByCourse(course);
    }
    @Override
    public List<Course> getAll() {
        return courseRepository.getAll();
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
