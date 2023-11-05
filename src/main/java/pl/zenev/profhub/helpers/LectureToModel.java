package pl.zenev.profhub.helpers;


import jakarta.enterprise.context.ApplicationScoped;
import models.CourseModel;
import models.LectureModel;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.entities.Lecture;

import java.util.function.Function;

@ApplicationScoped
public class LectureToModel  implements Function<Lecture, LectureModel> {
    @Override
    public LectureModel apply(Lecture lecture) {
        return LectureModel.builder()
                .lengthInMinutes(lecture.getLengthInMinutes())
                .id(lecture.getUuid()).build();
    }
}
