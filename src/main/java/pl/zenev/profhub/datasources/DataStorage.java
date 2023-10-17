package pl.zenev.profhub.datasources;

import pl.zenev.profhub.models.Professor;

import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    public List<Professor> professors = new ArrayList<>(
    );
    public DataStorage(){
        professors.add(new Professor("Nick", 53));
        professors.add(new Professor("Alex", 45));
        professors.add(new Professor("Mary", 76));
    }
}
