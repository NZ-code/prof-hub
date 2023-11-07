package pl.zenev.profhub.helpers;

import jakarta.enterprise.context.ApplicationScoped;
import pl.zenev.profhub.dto.GetLectureResponse;
import pl.zenev.profhub.entities.Lecture;

import java.util.function.Function;

@ApplicationScoped
public class LectureToResponse implements Function<Lecture, GetLectureResponse> {
    @Override
    public GetLectureResponse apply(Lecture lecture) {
        return GetLectureResponse.builder()
                .id(lecture.getUuid())
                .name(lecture.getName())
                .build();
    }
}