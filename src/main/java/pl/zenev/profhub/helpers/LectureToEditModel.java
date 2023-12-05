package pl.zenev.profhub.helpers;

import jakarta.enterprise.context.ApplicationScoped;
import pl.zenev.profhub.entities.Lecture;
import pl.zenev.profhub.models.LectureEditModel;

import java.util.function.Function;

@ApplicationScoped
public class LectureToEditModel implements Function<Lecture, LectureEditModel> {
    @Override
    public LectureEditModel apply(Lecture lecture) {
        return LectureEditModel.builder()
                .id(lecture.getUuid())
                .name(lecture.getName())
                .lengthInMinutes(lecture.getLengthInMinutes())
                .version(lecture.getVersion())
                .build();
    }
}