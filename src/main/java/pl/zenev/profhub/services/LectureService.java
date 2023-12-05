package pl.zenev.profhub.services;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
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
//@RolesAllowed(UserRoles.USER)
public class LectureService implements Service<Lecture>{
    final LectureRepository lectureRepository;
    final CourseRepository courseRepository;
    private final SecurityContext securityContext;
    private final ProfessorRepository professorRepository;
    private final Logger logger;
    @Inject
    public LectureService(LectureRepository lectureRepository, CourseRepository courseRepository, SecurityContext securityContext, ProfessorRepository professorRepository, Logger logger) {
        this.lectureRepository = lectureRepository;
        this.courseRepository = courseRepository;
        this.securityContext = securityContext;
        this.professorRepository = professorRepository;
        this.logger = logger;
    }

    @Override
   // @RolesAllowed(UserRoles.USER)
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
                Professor user = getProfessorFromSecurityContext();
                return lectureRepository.findByUser(user);
            }
    }

    @Override
    public Optional<Lecture> getById(UUID uuid) {
        return lectureRepository.getById(uuid);
    }

    @Override
    //@Transactional
    //@PermitAll
    @RolesAllowed(UserRoles.USER)
    public void add(Lecture lecture) {
        Professor user = getProfessorFromSecurityContext();
        lecture.setProfessor(user);
        lectureRepository.add(lecture);
        if (securityContext.getCallerPrincipal() != null){
            Professor userForLog = professorRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                    .orElseThrow(IllegalStateException::new);
            logger.info("User" + userForLog.getId() + "created lecture with id " + lecture.getUuid());
        }
    }
    public void addInit(Lecture lecture) {

        lectureRepository.add(lecture);
    }
    //@RolesAllowed(UserRoles.USER)
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
    //@RolesAllowed(UserRoles.USER)
    public void delete(UUID id) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            lectureRepository.delete(id);
            if (securityContext.getCallerPrincipal() != null){
                Professor userForLog = professorRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                        .orElseThrow(IllegalStateException::new);
                logger.info("\nUser " + userForLog.getId() + " delete lecture with id " + id);
            }
        }
        else{
            Professor user = getProfessorFromSecurityContext();
            var userLectures= lectureRepository.findByUser(user);
            int size= userLectures.stream().filter(lecture -> lecture.getUuid().equals(id)).toList().size();
            if(size >0){
                lectureRepository.delete(id);
                if (securityContext.getCallerPrincipal() != null){
                    Professor userForLog = professorRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                            .orElseThrow(IllegalStateException::new);
                    logger.info("\nUser " + userForLog.getId() + " delete lecture with id " + id);
                }
            }
        }

    }

    private Professor getProfessorFromSecurityContext() {
        String login = securityContext.getCallerPrincipal().getName();
        System.out.println("Login:" + login);
        Professor user = professorRepository.findByLogin(login)
                .orElseThrow(IllegalStateException::new);
        return user;
    }

    //@Transactional
    //@RolesAllowed(UserRoles.USER)
    public void update(Lecture lecture) {

        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            lectureRepository.update(lecture);
            if (securityContext.getCallerPrincipal() != null){
                Professor userForLog = professorRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                        .orElseThrow(IllegalStateException::new);
                logger.info("\nUser " + userForLog.getId() + " updated lecture with id " + lecture.getUuid());
            }
        }
        else{
            Professor user = getProfessorFromSecurityContext();
            var userLectures= lectureRepository.findByUser(user);
            int size= userLectures.stream().filter(l -> l.getUuid().equals(lecture.getUuid())).toList().size();
            if(size >0){
                lectureRepository.update(lecture);
                if (securityContext.getCallerPrincipal() != null){
                    Professor userForLog = professorRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                            .orElseThrow(IllegalStateException::new);
                    logger.info("\nUser " + userForLog.getId() + " updated lecture with id " + lecture.getUuid());
                }
            }
        }

    }

    public List<Lecture> getAllInit() {
        return lectureRepository.getAll();
    }
}
