package pl.zenev.profhub.views;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.zenev.profhub.models.CoursesModel;
import pl.zenev.profhub.helpers.CoursesToModel;
import pl.zenev.profhub.services.CourseService;

@RequestScoped
@Named
public class CoursesList {
    private final CourseService courseService;
    private CoursesModel coursesList;
    private CoursesToModel coursesToModel;
    @Inject
    public CoursesList(CourseService courseService, CoursesToModel coursesToModel){
        this.courseService = courseService;
        this.coursesToModel = coursesToModel;
    }
    public CoursesModel getCourses(){
        if(coursesList==null){
            coursesList = coursesToModel.apply(courseService.getAll());
        }
        return coursesList;
    }
    public String deleteAction(CoursesModel.Course course) {
        courseService.delete(course.getId());
        return "courses_list?faces-redirect=true";
    }
}
