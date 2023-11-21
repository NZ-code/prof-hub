package pl.zenev.profhub.helpers;

import jakarta.enterprise.context.ApplicationScoped;
import pl.zenev.profhub.dto.PatchCourseRequest;
import pl.zenev.profhub.dto.PatchProfessorRequest;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.entities.Professor;

import java.util.function.BiFunction;

@ApplicationScoped
public class PatchRequestToProfessor implements BiFunction<Professor, PatchProfessorRequest, Professor> {
    @Override
    public Professor apply(Professor professor, PatchProfessorRequest patchProfessorRequest) {
        return Professor
                .builder()
                .id(professor.getId())
                .name(patchProfessorRequest.getName())
                .build();
    }
}
