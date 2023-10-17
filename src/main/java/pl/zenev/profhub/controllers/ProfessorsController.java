package pl.zenev.profhub.controllers;

import pl.zenev.profhub.dto.GetProfessorResponse;
import pl.zenev.profhub.dto.GetProfessorsResponse;
import pl.zenev.profhub.models.Professor;
import pl.zenev.profhub.services.ProfessorService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProfessorsController {
    private ProfessorService professorService;
    public ProfessorsController(ProfessorService professorService){
        this.professorService = professorService;
    }
    public GetProfessorsResponse getAllProfessors(){
        List<Professor> professorList = professorService.getAllProfessors();
        return new GetProfessorsResponse(professorList);
    }

    public GetProfessorResponse getProfessorById(UUID uuid) {
        GetProfessorResponse getProfessorResponse = null;
        Optional<Professor> professor = professorService.getProfessorById(uuid);
        if(professor.isPresent()){
            getProfessorResponse = new GetProfessorResponse(professor.get());
        }
        return getProfessorResponse;
    }
}
