package pl.zenev.profhub.configuration;


import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RunAs;
import jakarta.ejb.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.servlet.annotation.WebListener;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.entities.Domain;
import pl.zenev.profhub.entities.Lecture;
import pl.zenev.profhub.entities.Professor;
import pl.zenev.profhub.security.UserRoles;
import pl.zenev.profhub.services.CourseService;
import pl.zenev.profhub.services.LectureService;
import pl.zenev.profhub.services.ProfessorService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

//@Singleton
//@Startup
//@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
//@NoArgsConstructor
//@DependsOn("InitializeAdminService")
//@DeclareRoles({UserRoles.ADMIN, UserRoles.USER})
//@RunAs(UserRoles.ADMIN)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@ApplicationScoped
//@Log
public class InitializedData {
    private static boolean INITIALIZED = false;
    private Pbkdf2PasswordHash passwordHash;
    private CourseService courseService;
    private ProfessorService professorService;
    //private RequestContextController requestContextController;
    private LectureService lectureService;
    private final RequestContextController requestContextController;

    @Inject
    private SecurityContext securityContext;

//    @Inject
//    public InitializedData(CourseService courseService, ProfessorService professorService, LectureService lectureService
//            , RequestContextController requestContextController) {
//        this.courseService = courseService;
//        this.professorService = professorService;
//        this.requestContextController =requestContextController;
//        this.lectureService = lectureService;
//    }
    @EJB
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
    @EJB
    public void setProfessorService(ProfessorService professorService) {
        this.professorService = professorService;
    }
    @EJB
    public void setLectureService(LectureService lectureService) {
        this.lectureService = lectureService;
    }

//    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
//        init();
//    }
    @Inject
    public InitializedData(RequestContextController requestContextController, Pbkdf2PasswordHash passwordHash){
        this.requestContextController = requestContextController;
        this.passwordHash = passwordHash;
    }
    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        requestContextController.activate();
        if (professorService.getAll().size()<=1 && lectureService.getAllInit().isEmpty() && courseService.getAllInit().isEmpty()){
            init();
        }
        requestContextController.deactivate();
    }
    //@PostConstruct
    @SneakyThrows
    private void init() {
        //if (professorService.getAll().size()<=1) {
            //requestContextController.activate();// start request scope in order to inject request scoped repositories
            Professor professorNick = Professor.builder()
                    .id(UUID.fromString("56804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                    .name("nick")
                    .login("nick")
                    .password("nick")
                    .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                    .build();
                    // new Professor("Nick", 53);
            Professor professorAlex = Professor.builder()
                    .id(UUID.fromString("67804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                    .name("alex")
                    .login("alex")
                    .password("alex")
                    .roles(List.of(UserRoles.USER))
                    .build();
//            Professor professorMary = new Professor("Mary", 76);
//            Professor professorJohn = new Professor("John", 88);

            professorService.add(professorNick);
            professorService.add(professorAlex);
//            professorService.add(professorMary);
//            professorService.add(professorJohn);


            // courses and lectures


            Course mathCourse = new Course("Financial Mathematics", new Date(2023, 10, 22), Domain.MATH);

            Course physicsCourse = new Course("Introduction to Nuclear Engineering", new Date(2023, 10, 22), Domain.PHYSICS);
            Course chemistryCourse = new Course("Chemistry for Beginners", new Date(2023, 10, 22), Domain.CHEMISTRY);

            courseService.addInit(mathCourse);
            courseService.addInit(physicsCourse);
            courseService.addInit(chemistryCourse);

            Lecture lectureA1 = new Lecture("Introduction, Financial Terms and Concepts", mathCourse, 106, professorNick);
            Lecture lectureA2 = new Lecture("Linear Algebra", mathCourse, 140, professorAlex);

            //Lecture lectureB1 = new Lecture("Radiation History to the Present", physicsCourse, 58, professorMary);
            //Lecture lectureB2 = new Lecture("Radiation Utilizing", physicsCourse, 169, professorMary);

            //Lecture lectureC1 = new Lecture("Atoms, compounds, and ions", physicsCourse, 134, professorMary);
            //Lecture lectureC2 = new Lecture("Electronic structure of atoms", physicsCourse, 168, professorJohn);


            lectureService.addInit(lectureA1);
            lectureService.addInit(lectureA2);

//            lectureService.add(lectureB1);
//            lectureService.add(lectureB2);
//
//            lectureService.add(lectureC1);
//            lectureService.add(lectureC2);

            System.out.println("------------------Presentation----------------");
            System.out.println("------------------Courses");
           // List<Course> courseList = courseService.getAll();
        //List<Lecture> lecturesList = lectureService.getAll();
//            for (var course : courseList) {
//                System.out.println(course);
//            }
//            System.out.println("------------------Lectures");
//            for (var lecture : lecturesList) {
//                System.out.println(lecture);
//            }
            //requestContextController.deactivate();
        }
   // }
}
