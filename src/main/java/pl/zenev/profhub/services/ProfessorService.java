package pl.zenev.profhub.services;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.servlet.ServletContext;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import pl.zenev.profhub.entities.Professor;
import pl.zenev.profhub.repositories.ProfessorRepository;
import pl.zenev.profhub.security.UserRoles;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static pl.zenev.profhub.security.UserRoles.USER;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
//@RolesAllowed(UserRoles.USER)
public class ProfessorService implements Service<Professor> {
    private ProfessorRepository professorRepository;
    private ServletContext context;
    private FileService fileService;
    private final Pbkdf2PasswordHash passwordHash;
    //private Logger logger;

    private final SecurityContext securityContext;


    @Inject
    public ProfessorService(ProfessorRepository professorRepository,
                            @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash,  SecurityContext securityContext) {
        this.professorRepository = professorRepository;
       //this.fileService = fileService;
        //this.context = context;
        this.passwordHash = passwordHash;
        //this.logger = logger;
        this.securityContext = securityContext;
    }

   // @PermitAll
    //@RolesAllowed(UserRoles.ADMIN)
    public List<Professor> getAll(){
        return professorRepository.getAll();
    }

   // @RolesAllowed(UserRoles.ADMIN)
    public Optional<Professor> getById(UUID uuid) {
        return professorRepository.getById(uuid);
    }


    //@Transactional
    @PermitAll
    public void add(Professor professor) {
        if (professorRepository.getById(professor.getId()).isPresent()){
            throw new IllegalArgumentException("User already exists.");
        }
        passwordHash.initialize(Map.of(
                "Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA256",
                "Pbkdf2PasswordHash.Iterations", "2048",
                "Pbkdf2PasswordHash.SaltSizeBytes", "32",
                "Pbkdf2PasswordHash.KeySizeBytes", "32"
        ));
        if (professor.getRoles().isEmpty()){
            professor.setRoles(List.of(USER));
        }
        professor.setPassword(passwordHash.generate(professor.getPassword().toCharArray()));
        professorRepository.add(professor);
        if (securityContext.getCallerPrincipal() != null){
            Professor userForLog = professorRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                    .orElseThrow(IllegalStateException::new);
           // logger.info("User" + userForLog.getId() + "created user with id " + professor.getId());
        }
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
    @RolesAllowed(UserRoles.ADMIN)
    public void update(Professor professor) {
        professorRepository.update(professor);
    }

    //@RolesAllowed(UserRoles.ADMIN)
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
