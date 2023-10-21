package pl.zenev.profhub.repositories;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.datasources.DataStorage;
import pl.zenev.profhub.models.Professor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@NoArgsConstructor(force = true)
public class ProfessorRepository {

    private DataStorage dataStorage;
    @Inject
    public ProfessorRepository(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public List<Professor> getAllProfessors(){
        return dataStorage.getProfessors();
    }

    public Optional<Professor> getProfessorById(UUID uuid) {
        return dataStorage.getProfessors().stream().filter(professor -> professor.getId().equals(uuid)).findFirst();
    }

    public void update(Professor professor) {
        if(dataStorage.getProfessors().removeIf(professorStored -> professorStored.getId().equals(professor.getId()))){
            dataStorage.getProfessors().add(professor);
        }
        else{
            throw new IllegalArgumentException("The professor with id \"%s\" does not exist".formatted(professor.getId()));
        }

    }

    public byte[] getImage(UUID uuid) {
        Optional<Professor> professorOpt = getProfessorById(uuid);
        if(professorOpt.isPresent()){
            return professorOpt.get().getPicture();
        }
        else{
            throw new IllegalArgumentException("The professor with id \"%s\" does not exist".formatted(uuid));
        }

    }
}
