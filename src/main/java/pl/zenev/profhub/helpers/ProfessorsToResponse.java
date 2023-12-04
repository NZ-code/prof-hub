package pl.zenev.profhub.helpers;

import jakarta.enterprise.context.ApplicationScoped;
import pl.zenev.profhub.dto.GetCoursesResponse;
import pl.zenev.profhub.dto.GetProfessorsResponse;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.entities.Professor;

import java.util.List;
import java.util.function.Function;

@ApplicationScoped
public class ProfessorsToResponse implements Function<List<Professor>, GetProfessorsResponse> {

    @Override
    public GetProfessorsResponse apply(List<Professor> entities) {
        return GetProfessorsResponse.builder()
                .professors(
                        entities.stream()
                                .map(professor -> GetProfessorsResponse.Professor.builder()
                                        .id(professor.getId())
                                        .name(professor.getName())
                                        .login(professor.getLogin())
                                        .password(professor.getPassword())
                                        .role(professor.getRoles().toString())
                                        .build()).toList())
                .build();
    }
}
