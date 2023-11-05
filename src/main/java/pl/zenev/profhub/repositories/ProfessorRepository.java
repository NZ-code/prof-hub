package pl.zenev.profhub.repositories;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.datasources.DataStorage;
import pl.zenev.profhub.entities.Professor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@NoArgsConstructor(force = true)
public class ProfessorRepository implements Repository<Professor>{

    private DataStorage dataStorage;
    @Inject
    public ProfessorRepository(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public List<Professor> getAll(){
        return dataStorage.getProfessors();
    }

    public Optional<Professor> getById(UUID uuid) {
        return dataStorage.getProfessorById(uuid);
    }

    @Override
    public void add(Professor professor) {
        dataStorage.addProfessor(professor);
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
        Optional<Professor> professorOpt = getById(uuid);
        if(professorOpt.isPresent()){
            return professorOpt.get().getPicture();
        }
        else{
            throw new IllegalArgumentException("The professor with id \"%s\" does not exist".formatted(uuid));
        }

    }
}
