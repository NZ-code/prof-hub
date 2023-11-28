package pl.zenev.profhub.helpers;

import jakarta.enterprise.context.ApplicationScoped;
import pl.zenev.profhub.dto.PutCourseRequest;
import pl.zenev.profhub.dto.PutProfessorRequest;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.entities.Professor;
import pl.zenev.profhub.security.UserRoles;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

@ApplicationScoped
public class RequestToProfessor implements BiFunction<UUID, PutProfessorRequest, Professor> {
    @Override
    public Professor apply(UUID uuid, PutProfessorRequest putProfessorRequest) {
        return Professor
                .builder()
                .id(uuid)
                .name(putProfessorRequest.getName())
                .login(putProfessorRequest.getLogin())
                .password(putProfessorRequest.getPassword())
                .roles(List.of(UserRoles.USER))
                .build();
    }
}
