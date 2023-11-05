package pl.zenev.profhub.helpers;

import models.LecturesModel;
import pl.zenev.profhub.entities.Lecture;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class LecturesToModel implements Function<List<Lecture>, LecturesModel>, Serializable {

    @Override
    public LecturesModel apply(List<Lecture> lectures) {
        return LecturesModel.builder()
                .lectures(lectures.stream()
                        .map(lecture -> LecturesModel
                                .Lecture
                                .builder()
                                .name(lecture.getName())
                                .lengthInMinutes(lecture.getLengthInMinutes())
                                .id(lecture.getUuid()).build())
                        .toList())
                .build();
    }
}