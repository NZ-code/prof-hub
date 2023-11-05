package pl.zenev.profhub.helpers;


import jakarta.enterprise.context.ApplicationScoped;
import pl.zenev.profhub.models.LectureModel;
import pl.zenev.profhub.entities.Lecture;

import java.util.function.Function;

@ApplicationScoped
public class LectureToModel  implements Function<Lecture, LectureModel> {
    @Override
    public LectureModel apply(Lecture lecture) {
        return LectureModel.builder()
                .id(lecture.getUuid())
                .name(lecture.getName())
                .lengthInMinutes(lecture.getLengthInMinutes())
                .build();
    }
}
