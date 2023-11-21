package pl.zenev.profhub.helpers;

import jakarta.enterprise.context.ApplicationScoped;
import pl.zenev.profhub.dto.GetCourseResponse;
import pl.zenev.profhub.dto.GetProfessorResponse;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.entities.Professor;

import java.util.function.Function;

@ApplicationScoped
public class ProfessorToResponse implements Function<Professor, GetProfessorResponse> {
    @Override
    public GetProfessorResponse apply(Professor professor) {
        return GetProfessorResponse
                .builder()
                .uuid(professor.getId())
                .name(professor.getName())
                .build();
    }
}
