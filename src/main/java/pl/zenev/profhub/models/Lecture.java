package pl.zenev.profhub.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
@EqualsAndHashCode
public class Lecture {

    private float lengthInMinutes;
    private String name;
    private String professorName;

    private Course course;
    private Professor professor;
}
