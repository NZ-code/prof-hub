package pl.zenev.profhub.views;


import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import models.CourseModel;
import models.LecturesModel;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.helpers.CourseToModel;
import pl.zenev.profhub.helpers.LecturesToModel;
import pl.zenev.profhub.services.CourseService;
import pl.zenev.profhub.services.LectureService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@ViewScoped
@Named
public class CourseView implements Serializable {
    CourseService courseService;
    LectureService lectureService;
    CourseModel courseModel;
    CourseToModel courseToModel;
    LecturesModel lecturesModel;
    LecturesToModel lecturesToModel;

    @Getter
    @Setter
    private String id;

    @Inject
    public CourseView(CourseService courseService, CourseToModel courseToModel,LectureService lectureService, LecturesToModel lecturesToModel) {
        this.courseService = courseService;
        this.courseToModel = courseToModel;
        this.lectureService = lectureService;
        this.lecturesToModel = lecturesToModel;
    }
    public void init() throws IOException {
        UUID uuid = UUID.fromString(id);
        Optional<Course> course = courseService.getById(uuid);
        if(course.isPresent()){
            this.courseModel = courseToModel.apply(course.get());
            var doneLectures = lectureService.getAllByCourseId(uuid);
            this.lecturesModel = lecturesToModel.apply(doneLectures);
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Lecture not found");
        }
    }


}
