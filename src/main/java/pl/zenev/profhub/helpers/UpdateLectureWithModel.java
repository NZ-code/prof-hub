package pl.zenev.profhub.helpers;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.SneakyThrows;
import pl.zenev.profhub.entities.Lecture;
import pl.zenev.profhub.models.LectureEditModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.function.BiFunction;
@ApplicationScoped
public class UpdateLectureWithModel implements BiFunction<Lecture, LectureEditModel, Lecture>, Serializable {
    @Override
    @SneakyThrows
    public Lecture apply(Lecture lecture, LectureEditModel lectureEditModel) {
        return Lecture.builder()
                .uuid(lecture.getUuid())
                .name(lectureEditModel.getName())
                .lengthInMinutes(lectureEditModel.getLengthInMinutes())
                .version(lectureEditModel.getVersion())
                .professor(lecture.getProfessor())
                .course(lecture.getCourse())
                .build();
    }
}
