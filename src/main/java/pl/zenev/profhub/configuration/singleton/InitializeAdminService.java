package pl.zenev.profhub.configuration.singleton;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import pl.zenev.profhub.entities.Professor;
import pl.zenev.profhub.repositories.ProfessorRepository;
import pl.zenev.profhub.security.UserRoles;
import pl.zenev.profhub.services.ProfessorService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
public class InitializeAdminService {

    private final Pbkdf2PasswordHash passwordHash;
    private final ProfessorRepository professorRepository;

    @Inject
    public InitializeAdminService(
            ProfessorRepository professorRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.professorRepository = professorRepository;
        this.passwordHash = passwordHash;
    }
    @PostConstruct
    @SneakyThrows
    private void init() {
        if (professorRepository.findByLogin("admin-service").isEmpty()) {

            Professor admin = Professor.builder()
                    .id(UUID.fromString("14d59f3a-057c-44d5-825a-19295a6600a8"))
                    .login("admin-service")
                    .name("Admin")
                    .age(45)
                    .password(passwordHash.generate("adminadmin".toCharArray()))
                    .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                    .build();

            professorRepository.add(admin);
        }
    }
}
