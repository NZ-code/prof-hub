//package pl.zenev.profhub.controllers;
//
//import jakarta.enterprise.context.RequestScoped;
//import jakarta.inject.Inject;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.NoArgsConstructor;
//import pl.zenev.profhub.dto.GetProfessorResponse;
//import pl.zenev.profhub.dto.GetProfessorsResponse;
//import pl.zenev.profhub.entities.Professor;
//import pl.zenev.profhub.services.ProfessorService;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//@RequestScoped
//@NoArgsConstructor(force = true)
//public class ProfessorsControllerOld {
//    private ProfessorService professorService;
//    @Inject
//    public ProfessorsControllerOld(ProfessorService professorService){
//        this.professorService = professorService;
//    }
//    public GetProfessorsResponse getAllProfessors(){
//        List<Professor> professorList = professorService.getAll();
//
//        //return new GetProfessorsResponseprofessorList);
//    }
//
//    public GetProfessorResponse getProfessorById(HttpServletResponse response, UUID uuid) throws IOException {
//
//        GetProfessorResponse getProfessorResponse = null;
//        Optional<Professor> professor = professorService.getById(uuid);
//        if(professor.isPresent()){
//            getProfessorResponse = new GetProfessorResponse(professor.get());
//        }
//        else{
//            return null;
//        }
//
//        return getProfessorResponse;
//    }
//
//    public byte[] getImage(UUID uuid) {
//        return professorService.getProfessorImage(uuid);
//    }
//    public void deleteImage(UUID uuid){
//        professorService.deletePortrait(uuid);
//    }
//    public void putImage(UUID uuid, InputStream is) {
//        professorService.updatePortrait(uuid, is);
//    }
//}
