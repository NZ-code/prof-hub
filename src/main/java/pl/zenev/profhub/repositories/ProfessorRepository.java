package pl.zenev.profhub.repositories;

import pl.zenev.profhub.datasources.DataStorage;
import pl.zenev.profhub.models.Professor;

import java.util.ArrayList;
import java.util.List;

public class ProfessorRepository {

    private DataStorage dataStorage;

    public ProfessorRepository(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public List<Professor> getAllProfessors(){
        return dataStorage.professors;
    }
}
