package pl.zenev.profhub.helpers;



import jakarta.enterprise.context.ApplicationScoped;
import pl.zenev.profhub.dto.PutLectureRequest;
import pl.zenev.profhub.entities.Lecture;

import java.util.UUID;
import java.util.function.BiFunction;

@ApplicationScoped
public class RequestToLecture implements BiFunction<UUID, PutLectureRequest, Lecture> {
    @Override
    public Lecture apply(UUID uuid, PutLectureRequest putLectureRequest) {
        return Lecture
                .builder()
                .uuid(uuid)
                .name(putLectureRequest.getName())
                .build();
    }
}
