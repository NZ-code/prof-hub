package pl.zenev.profhub.views;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import lombok.Getter;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.models.CoursesModel;
import pl.zenev.profhub.helpers.CoursesToModel;
import pl.zenev.profhub.services.CourseService;

import static pl.zenev.profhub.security.UserRoles.ADMIN;

@RequestScoped
@Named
public class CoursesList {
    private  CourseService courseService;
    private CoursesModel coursesList;
    private CoursesToModel coursesToModel;
    private final SecurityContext securityContext;

    @Getter
    private boolean adminit = false;

    @EJB
    public void setCourseService(CourseService courseService ){
        this.courseService = courseService;
    }
    @Inject
    public CoursesList( CoursesToModel coursesToModel, SecurityContext securityContext){
        this.securityContext = securityContext;
        this.coursesToModel = coursesToModel;
    }
    public CoursesModel getCourses(){
        if(coursesList==null){

            coursesList = coursesToModel.apply(courseService.getAll());
        }
        if (securityContext.isCallerInRole(ADMIN)){
            this.adminit = true;
        }
        return coursesList;
    }
    public String deleteAction(Course course) {
        courseService.delete(course);
        return "courses_list?faces-redirect=true";
    }
}
