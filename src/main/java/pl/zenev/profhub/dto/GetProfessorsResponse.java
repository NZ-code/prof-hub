package pl.zenev.profhub.dto;

import lombok.*;
import pl.zenev.profhub.entities.Professor;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class GetProfessorsResponse
{
    public GetProfessorsResponse(List<Professor> professors) {
        this.professors = professors;
    }

    private List<Professor> professors;
}
