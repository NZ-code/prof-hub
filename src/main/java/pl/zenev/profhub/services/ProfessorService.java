package pl.zenev.profhub.services;

import pl.zenev.profhub.models.Professor;
import pl.zenev.profhub.repositories.ProfessorRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProfessorService {
    private ProfessorRepository professorRepository;

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
