package pl.zenev.profhub.views;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.models.CoursesModel;
import pl.zenev.profhub.helpers.CoursesToModel;
import pl.zenev.profhub.services.CourseService;

@RequestScoped
@Named
public class CoursesList {
    private  CourseService courseService;
    private CoursesModel coursesList;
    private CoursesToModel coursesToModel;

    @EJB
    public void setCourseService(CourseService courseService){
        this.courseService = courseService;
    }
    @Inject
    public CoursesList( CoursesToModel coursesToModel){
        this.coursesToModel = coursesToModel;
    }
    public CoursesModel getCourses(){
        if(coursesList==null){
            coursesList = coursesToModel.apply(courseService.getAll());
        }
        return coursesList;
    }
    public String deleteAction(Course course) {
        courseService.delete(course);
        return "courses_list?faces-redirect=true";
    }
}
