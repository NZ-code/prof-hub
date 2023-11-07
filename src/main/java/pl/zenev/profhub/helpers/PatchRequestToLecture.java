package pl.zenev.profhub.helpers;

import jakarta.enterprise.context.ApplicationScoped;
import pl.zenev.profhub.dto.PatchLectureRequest;
import pl.zenev.profhub.entities.Lecture;
import pl.zenev.profhub.models.LecturesModel;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

@ApplicationScoped
public class PatchRequestToLecture implements BiFunction<UUID,PatchLectureRequest, Lecture> {
    @Override
    public Lecture apply(UUID id, PatchLectureRequest patchRequestToLecture) {
        return Lecture
                .builder()
                .uuid(id)
                .name(patchRequestToLecture.getName())
                .build();
    }
}
