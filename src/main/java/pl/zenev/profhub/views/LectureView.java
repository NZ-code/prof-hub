package pl.zenev.profhub.views;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.zenev.profhub.models.LectureModel;
import pl.zenev.profhub.entities.Lecture;
import pl.zenev.profhub.helpers.LectureToModel;

import pl.zenev.profhub.services.LectureService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class LectureView implements Serializable {
    @Getter
    @Setter
    private String id;

    @Getter
    private LectureModel lecture;

    private LectureService lectureService;
    private LectureToModel lectureToModel;
    @EJB
    public void setLectureService(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @Inject
    public LectureView( LectureToModel lectureToModel){
        this.lectureToModel = lectureToModel;
    }
    public void init() throws IOException {
        UUID uuid = UUID.fromString(id);
        Optional<Lecture> finishedLecture = lectureService.getById(uuid);
        if (finishedLecture.isPresent()){
            this.lecture =lectureToModel.apply(finishedLecture.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Lecture not found");
        }
    }
}
