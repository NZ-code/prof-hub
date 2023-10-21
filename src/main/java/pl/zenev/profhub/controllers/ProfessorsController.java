package pl.zenev.profhub.controllers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.dto.GetProfessorResponse;
import pl.zenev.profhub.dto.GetProfessorsResponse;
import pl.zenev.profhub.models.Professor;
import pl.zenev.profhub.services.ProfessorService;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@RequestScoped
@NoArgsConstructor(force = true)
public class ProfessorsController {
    private ProfessorService professorService;
    @Inject
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

    public byte[] getImage(UUID uuid) {
        return professorService.getProfessorImage(uuid);
    }

    public void putImage(UUID uuid, InputStream is) {
        professorService.updatePortrait(uuid, is);
    }
}
