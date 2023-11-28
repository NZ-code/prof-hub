package pl.zenev.profhub.dto;

import lombok.*;
import pl.zenev.profhub.entities.Professor;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetProfessorsResponse
{
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Professor{
        private UUID id;

        private String name;
        private String login;
        private String password;
    }

    @Singular
    private List<GetProfessorsResponse.Professor> professors;
}
