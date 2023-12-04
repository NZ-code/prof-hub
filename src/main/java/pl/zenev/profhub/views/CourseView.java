package pl.zenev.profhub.views;


import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotFoundException;
import lombok.Getter;
import lombok.Setter;
import pl.zenev.profhub.models.CourseModel;
import pl.zenev.profhub.models.LecturesModel;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.helpers.CourseToModel;
import pl.zenev.profhub.helpers.LecturesToModel;
import pl.zenev.profhub.services.CourseService;
import pl.zenev.profhub.services.LectureService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class CourseView implements Serializable {
    private CourseService courseService;
    private LectureService lectureService;
    @Getter
    private CourseModel course;
    private CourseToModel courseToModel;
    @Getter
    private LecturesModel lecturesModel;
    private LecturesToModel lecturesToModel;

    @Getter
    @Setter
    private String id;
    @EJB
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
    @EJB
    public void setLectureService(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @Inject
    public CourseView( CourseToModel courseToModel, LecturesToModel lecturesToModel) {
        this.courseToModel = courseToModel;
        this.lecturesToModel = lecturesToModel;
    }
    public void init() throws IOException {
        UUID uuid = UUID.fromString(id);
        System.out.println(uuid.toString());
        Optional<Course> courseEntity = courseService.getById(uuid);
        System.out.println(courseEntity);
        if(courseEntity.isPresent()){
            this.course = courseToModel.apply(courseEntity.get());
            var doneLectures = lectureService.getAllByCourseId(uuid);
            this.lecturesModel = lecturesToModel.apply(doneLectures.orElseThrow(NotFoundException::new));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Course not found");
        }
    }
    public String deleteLectureAction(LecturesModel.Lecture lecture){
        lectureService.delete(lecture.getId());
        return "courses_list?faces-redirect=true";
    }

}
