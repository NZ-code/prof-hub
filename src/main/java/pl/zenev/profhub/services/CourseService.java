package pl.zenev.profhub.services;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.entities.Lecture;
import pl.zenev.profhub.repositories.CourseRepository;
import pl.zenev.profhub.repositories.LectureRepository;
import pl.zenev.profhub.security.UserRoles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pl.zenev.profhub.security.UserRoles.ADMIN;


@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class CourseService implements Service<Course>{
    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;
    private final SecurityContext securityContext;
    private final Logger logger;;
    @Getter
    private boolean canDelete = false;
    @Inject
    public CourseService(CourseRepository courseRepository, LectureRepository lectureRepository,  SecurityContext securityContext
            ,Logger logger) {
        this.courseRepository = courseRepository;
        this.lectureRepository = lectureRepository;
        this.securityContext = securityContext;
        this.logger = logger;
        if (securityContext.isCallerInRole(ADMIN)){
            this.canDelete = true;
        }
    }

//    public List<Lecture> getCourseLectures(Course course){
//        return lectureRepository.getLecturesByCourse(course);
//    }
    @Override
    //@RolesAllowed(UserRoles.USER)
    public List<Course> getAll() {
        return courseRepository.getAll();
    }

    @Override
    @RolesAllowed(UserRoles.USER)
    public Optional<Course> getById(UUID uuid) {
        return courseRepository.getById(uuid);
    }
    //@RolesAllowed(UserRoles.USER)

    public List<Course> getAllInit() {
        return courseRepository.getAll();
    }

    @Override
    //@PermitAll
    //@Transactional
    public void add(Course course) {
        courseRepository.add(course);
    }
    public void addInit(Course course) {
        courseRepository.add(course);
    }
    //@Transactional
    //@RolesAllowed(UserRoles.ADMIN)
    public void delete(Course course) {
        courseRepository.delete(course);
        lectureRepository.deleteByCourseId(course.getUuid());

    }
    //@Transactional
    //@RolesAllowed(UserRoles.USER)
    public void update(Course course) {
        courseRepository.update(course);
    }
}
