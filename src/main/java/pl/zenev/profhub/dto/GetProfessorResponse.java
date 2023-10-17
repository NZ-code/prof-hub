package pl.zenev.profhub.dto;

import lombok.*;
import pl.zenev.profhub.models.Professor;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetProfessorResponse {

    private UUID uuid;
    private String name;
    private int age;
    public GetProfessorResponse(Professor professor) {

        this.uuid = professor.getId();
        this.age = professor.getAge();
        this.name = professor.getName();
    }
}
