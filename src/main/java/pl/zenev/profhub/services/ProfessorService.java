package pl.zenev.profhub.services;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.entities.Professor;
import pl.zenev.profhub.repositories.ProfessorRepository;
import pl.zenev.profhub.security.UserRoles;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
@RolesAllowed(UserRoles.USER)
public class ProfessorService implements Service<Professor> {
    private ProfessorRepository professorRepository;
    private ServletContext context;
    private FileService fileService;
    private final Pbkdf2PasswordHash passwordHash;



    @Inject
    public ProfessorService(FileService fileService, ProfessorRepository professorRepository,
    @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash) {
        this.professorRepository = professorRepository;
        this.fileService = fileService;
        //this.context = context;
        this.passwordHash = passwordHash;
    }

   // @PermitAll
    @RolesAllowed(UserRoles.ADMIN)
    public List<Professor> getAll(){
        return professorRepository.getAll();
    }

    @RolesAllowed(UserRoles.ADMIN)
    public Optional<Professor> getById(UUID uuid) {
        return professorRepository.getById(uuid);
    }


    //@Transactional
    @PermitAll
    public void add(Professor professor) {
        professor.setPassword(passwordHash.generate(professor.getPassword().toCharArray()));
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
    @RolesAllowed(UserRoles.ADMIN)
    public void delete(Professor professor) {
        professorRepository.delete(professor);
    }

    //@Transactional

    public void update(Professor professor) {
        professorRepository.update(professor);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public Optional<Professor> find(String login) {
        return professorRepository.findByLogin(login);
    }
    @PermitAll
    public boolean verify(String login, String password) {
        return find(login)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }
}
