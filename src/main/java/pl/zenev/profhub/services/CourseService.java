package pl.zenev.profhub.services;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.entities.Lecture;
import pl.zenev.profhub.repositories.CourseRepository;
import pl.zenev.profhub.repositories.LectureRepository;
import pl.zenev.profhub.security.UserRoles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class CourseService implements Service<Course>{
    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;
    @Inject
    public CourseService(CourseRepository courseRepository, LectureRepository lectureRepository) {
        this.courseRepository = courseRepository;
        this.lectureRepository = lectureRepository;
    }

//    public List<Lecture> getCourseLectures(Course course){
//        return lectureRepository.getLecturesByCourse(course);
//    }
    @Override
    @RolesAllowed(UserRoles.USER)
    public List<Course> getAll() {
        return courseRepository.getAll();
    }

    @Override
    @RolesAllowed(UserRoles.USER)
    public Optional<Course> getById(UUID uuid) {
        return courseRepository.getById(uuid);
    }
    @RolesAllowed(UserRoles.ADMIN)
    @Override
    //@Transactional
    public void add(Course course) {
        courseRepository.add(course);
    }

    //@Transactional
    @RolesAllowed(UserRoles.ADMIN)
    public void delete(Course course) {
        courseRepository.delete(course);
        lectureRepository.deleteByCourseId(course.getUuid());
    }
    //@Transactional
    @RolesAllowed(UserRoles.ADMIN)
    public void update(Course course) {
        courseRepository.update(course);
    }
}
