package pl.zenev.profhub.repositories;

import pl.zenev.profhub.datasources.DataStorage;
import pl.zenev.profhub.models.Professor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProfessorRepository {

    private DataStorage dataStorage;

    public ProfessorRepository(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public List<Professor> getAllProfessors(){
        return dataStorage.professors;
    }

    public Optional<Professor> getProfessorById(UUID uuid) {
        return dataStorage.professors.stream().filter(professor -> professor.getId().equals(uuid)).findFirst();
    }

    public void update(Professor professor) {
        if(dataStorage.professors.removeIf(professorStored -> professorStored.getId().equals(professor.getId()))){
            dataStorage.professors.add(professor);
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
