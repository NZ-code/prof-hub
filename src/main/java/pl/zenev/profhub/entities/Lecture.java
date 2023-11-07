package pl.zenev.profhub.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Lecture {
    private UUID uuid;
    private float lengthInMinutes;
    private String name;


    private Course course;
    private Professor professor;

    public Lecture( String name, Course course, float lengthInMinutes, Professor professor) {
        this.name = name;
        this.course = course;
        this.lengthInMinutes = lengthInMinutes;
        this.professor = professor;
        this.uuid = UUID.randomUUID();
    }
}
