package pl.zenev.profhub.helpers;

import jakarta.enterprise.context.ApplicationScoped;
import pl.zenev.profhub.dto.GetLecturesResponse;
import pl.zenev.profhub.entities.Lecture;

import java.util.List;
import java.util.function.Function;

@ApplicationScoped
public class LecturesToResponse implements Function<List<Lecture>, GetLecturesResponse> {
    @Override
    public GetLecturesResponse apply(List<Lecture> l) {

        return GetLecturesResponse
                .builder()
                .lectures(l.stream()
                        .map(lecture -> GetLecturesResponse.Lecture.builder()
                                .id(lecture.getUuid())
                                .name(lecture.getName())
                                .version(lecture.getVersion())
                                .creationDateTime(lecture.getCreationDateTime())
                                .updateDateTime(lecture.getUpdateDateTime())
                                .professor(lecture.getProfessor().getName()==null ? "" :lecture.getProfessor().getName())
                                .build()
                        ).toList())
                .build();
    }
}
