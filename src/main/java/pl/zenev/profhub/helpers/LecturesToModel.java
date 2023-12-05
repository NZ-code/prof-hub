package pl.zenev.profhub.helpers;

import jakarta.enterprise.context.ApplicationScoped;
import pl.zenev.profhub.models.LecturesModel;
import pl.zenev.profhub.entities.Lecture;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

@ApplicationScoped
public class LecturesToModel implements Function<List<Lecture>, LecturesModel>, Serializable {

    @Override
    public LecturesModel apply(List<Lecture> lectures) {
        return LecturesModel.builder()
                .lectures(lectures.stream()
                        .map(lecture -> LecturesModel
                                .Lecture
                                .builder()
                                .name(lecture.getName())
                                .creationDateTime(lecture.getCreationDateTime())
                                .updateDateTime(lecture.getUpdateDateTime())
                                .lengthInMinutes(lecture.getLengthInMinutes())
                                .id(lecture.getUuid()).build())
                        .toList())
                .build();
    }
}