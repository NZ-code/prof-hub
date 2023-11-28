package pl.zenev.profhub.services;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.entities.Lecture;
import pl.zenev.profhub.entities.Professor;
import pl.zenev.profhub.repositories.CourseRepository;
import pl.zenev.profhub.repositories.LectureRepository;
import pl.zenev.profhub.repositories.ProfessorRepository;
import pl.zenev.profhub.security.UserRoles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@LocalBean
@Stateless
@NoArgsConstructor(force = true)
@RolesAllowed(UserRoles.USER)
public class LectureService implements Service<Lecture>{
    final LectureRepository lectureRepository;
    final CourseRepository courseRepository;
    private final SecurityContext securityContext;
    private final ProfessorRepository professorRepository;
    @Inject
    public LectureService(LectureRepository lectureRepository, CourseRepository courseRepository, SecurityContext securityContext, ProfessorRepository professorRepository) {
        this.lectureRepository = lectureRepository;
        this.courseRepository = courseRepository;
        this.securityContext = securityContext;
        this.professorRepository = professorRepository;
    }

    @Override
    @RolesAllowed(UserRoles.USER)
    public List<Lecture> getAll() {
        String l = securityContext.getCallerPrincipal().getName();
        System.out.println("Login:" + l);
        //lectureRepository.getAll();
            if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
                String login = securityContext.getCallerPrincipal().getName();
                System.out.println("Login:" + login);
                return lectureRepository.getAll();
            }
            else{
                String login = securityContext.getCallerPrincipal().getName();
                System.out.println("Login:" + login);
                Professor user = professorRepository.findByLogin(login)
                        .orElseThrow(IllegalStateException::new);
                return lectureRepository.findByUser(user);
            }
    }

    @Override
    public Optional<Lecture> getById(UUID uuid) {
        return lectureRepository.getById(uuid);
    }

    @Override
    //@Transactional
    public void add(Lecture lecture) {
        lectureRepository.add(lecture);
    }

    public Optional<List<Lecture>> getAllByCourseId(UUID uuid) {
        System.out.println("course :" + courseRepository.getById(uuid));
        Optional<Course> course = courseRepository.getById(uuid);
        if(course.isEmpty()){
            return Optional.empty();
        }
        System.out.println("lectureRepository.getLecturesByCourseId(uuid)="+ lectureRepository.getLecturesByCourseId(uuid));
        return Optional.ofNullable(lectureRepository.getLecturesByCourseId(uuid));
    }
    //@Transactional
    public void delete(UUID id) {
        lectureRepository.delete(id);
    }
    //@Transactional
    public void update(Lecture lecture) {
        lectureRepository.update(lecture);
    }
}
