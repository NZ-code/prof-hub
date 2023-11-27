package pl.zenev.profhub.services;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.entities.Professor;
import pl.zenev.profhub.repositories.ProfessorRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class ProfessorService implements Service<Professor> {
    private ProfessorRepository professorRepository;
    private ServletContext context;
    private FileService fileService;


    @Inject
    public ProfessorService(FileService fileService, ProfessorRepository professorRepository,ServletContext context) {
        this.professorRepository = professorRepository;
        this.fileService = fileService;
        this.context = context;
    }

    public List<Professor> getAll(){
        return professorRepository.getAll();
    }

    public Optional<Professor> getById(UUID uuid) {
        return professorRepository.getById(uuid);
    }


    //@Transactional
    public void add(Professor professor) {
        professorRepository.add(professor);
    }

    public void deletePortrait(UUID id){
        String filePath = context.getInitParameter("filePath");
        filePath +=  id.toString() + ".jpg";
        fileService.deleteImage(filePath);
    }
    public void updatePortrait(UUID id, InputStream is) {
        String filePath = context.getInitParameter("filePath");
        filePath +=  id.toString() + ".jpg";
        System.out.println(filePath);
        try {
            fileService.writeBytesToFile(filePath, is.readAllBytes());
        }
        catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
//        String filePath = context.getInitParameter("filePath");
//        professorRepository.getById(id).ifPresent(professor -> {
//            try {
//
//                professor.setPicture(is.readAllBytes());
//                System.out.println("Picture set");
//                byte[] picture = professor.getPicture();
//                System.out.println(picture.length);
//                professorRepository.update(professor);
//            } catch (IOException ex) {
//                throw new IllegalStateException(ex);
//            }
//        });
    }

    public byte[] getProfessorImage(UUID id) {
        String filePath = context.getInitParameter("filePath");
        filePath += id.toString() + ".jpg";
        return fileService.readBytesFromFile(filePath);
    }

    //@Transactional
    public void delete(Professor professor) {
        professorRepository.delete(professor);
    }

    //@Transactional
    public void update(Professor professor) {
        professorRepository.update(professor);
    }
}
