package pl.zenev.profhub.datasources;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.models.Professor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped

public class DataStorage {
    @Getter
    private List<Professor> professors = new ArrayList<>(
    );
    public DataStorage(){
        professors.add(new Professor("Nick", 53, UUID.fromString("525d3e7b-bb1f-4c13-bf17-926d1a12e4c0")));
        professors.add(new Professor("Alex", 45, UUID.fromString("c78d464e-9df9-4b63-9268-60426b6fdcfa")));
        professors.add(new Professor("Mary", 76, UUID.fromString("4ec96d7a-5712-498f-96c3-8567a2981f22")));
        professors.add(new Professor("John", 88, UUID.fromString("11a1de81-1b71-4c1c-aaac-7be89b6615b7")));
    }
}
