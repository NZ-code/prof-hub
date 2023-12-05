package pl.zenev.profhub.views;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBTransactionRolledbackException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.zenev.profhub.entities.Lecture;
import pl.zenev.profhub.helpers.LectureToEditModel;
import pl.zenev.profhub.helpers.LectureToModel;
import pl.zenev.profhub.helpers.UpdateLectureWithModel;
import pl.zenev.profhub.models.LectureEditModel;
import pl.zenev.profhub.services.LectureService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class LectureEdit implements Serializable {
    @Getter
    @Setter
    private String id;

    @Getter
    private LectureEditModel lecture;

    private LectureService lectureService;
    private LectureToModel lectureToModel;
    private final FacesContext facesContext;
    private final UpdateLectureWithModel updateLectureWithModelFunction ;
    private final LectureToEditModel lectureToEditModel ;
    @EJB
    public void setLectureService(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @Inject
    public LectureEdit(LectureToModel lectureToModel, FacesContext facesContext, UpdateLectureWithModel updateLectureWithModelFunction, LectureToEditModel lectureToEditModel){
        this.lectureToModel = lectureToModel;
        this.facesContext = facesContext;
        this.updateLectureWithModelFunction = updateLectureWithModelFunction;
        this.lectureToEditModel = lectureToEditModel;
    }
    public void init() throws IOException {
        UUID uuid = UUID.fromString(id);
        Optional<Lecture> finishedLecture = lectureService.getById(uuid);
        if (finishedLecture.isPresent()){
            this.lecture =lectureToEditModel.apply(finishedLecture.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Lecture not found");
        }
    }
    public String saveAction() throws IOException {
        try {
            lectureService.update(updateLectureWithModelFunction.apply(lectureService.getById(UUID.fromString(id)).orElseThrow(), lecture));
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return viewId + "?faces-redirect=true&includeViewParams=true";
        } catch (EJBTransactionRolledbackException ex) {
            System.out.println(ex);
            if (ex.getCause().getCause() instanceof OptimisticLockException) {
                facesContext.addMessage(null, new FacesMessage("Version collision."));
            }

            return null ;
        }
    }
}
