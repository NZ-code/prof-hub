package pl.zenev.profhub.configuration;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import pl.zenev.profhub.models.Course;
import pl.zenev.profhub.models.Domain;
import pl.zenev.profhub.models.Professor;
import pl.zenev.profhub.services.CourseService;
import pl.zenev.profhub.services.ProfessorService;

import java.util.Date;
import java.util.UUID;

@ApplicationScoped
public class InitializedData {
    private final CourseService courseService;
    private final ProfessorService professorService;
    private final RequestContextController requestContextController;
    @Inject
    public InitializedData(CourseService courseService, ProfessorService professorService,RequestContextController requestContextController) {
        this.courseService = courseService;
        this.professorService = professorService;
        this.requestContextController =requestContextController;
    }
    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @SneakyThrows
    private void init() {
        requestContextController.activate();// start request scope in order to inject request scoped repositories

        professorService.add(new Professor("Nick", 53, UUID.fromString("525d3e7b-bb1f-4c13-bf17-926d1a12e4c0")));
        professorService.add(new Professor("Alex", 45, UUID.fromString("c78d464e-9df9-4b63-9268-60426b6fdcfa")));
        professorService.add(new Professor("Mary", 76, UUID.fromString("4ec96d7a-5712-498f-96c3-8567a2981f22")));
        professorService.add(new Professor("John", 88, UUID.fromString("11a1de81-1b71-4c1c-aaac-7be89b6615b7")));

        //lectures

        // courses
        courseService.add(new Course("Math in Finance", new Date(2023, 10,22), Domain.MATH));
        courseService.add(new Course("Nuclear Physics", new Date(2023, 10,22), Domain.PHYSICS));
        courseService.add(new Course("Chemistry for Beginners", new Date(2023, 10,22), Domain.CHEMISTRY));
    }
}
