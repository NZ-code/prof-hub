package pl.zenev.profhub.services;

import pl.zenev.profhub.models.Professor;
import pl.zenev.profhub.repositories.ProfessorRepository;

import java.util.List;

public class ProfessorService {
    private ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public List<Professor> getAllProfessors(){
        return professorRepository.getAllProfessors();
    }
}
