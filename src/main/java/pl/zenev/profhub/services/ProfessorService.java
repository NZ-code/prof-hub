package pl.zenev.profhub.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.models.Professor;
import pl.zenev.profhub.repositories.ProfessorRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class ProfessorService {
    private ProfessorRepository professorRepository;
    @Inject
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public List<Professor> getAllProfessors(){
        return professorRepository.getAllProfessors();
    }

    public Optional<Professor> getProfessorById(UUID uuid) {
        return professorRepository.getProfessorById(uuid);
    }
    public void updatePortrait(UUID id, InputStream is) {
        professorRepository.getProfessorById(id).ifPresent(professor -> {
            try {

                professor.setPicture(is.readAllBytes());
                System.out.println("Picture set");
                byte[] picture = professor.getPicture();
                System.out.println(picture.length);
                professorRepository.update(professor);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    public byte[] getProfessorImage(UUID uuid) {
        return professorRepository.getImage(uuid);
    }
}
