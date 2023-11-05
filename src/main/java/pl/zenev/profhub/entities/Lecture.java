package pl.zenev.profhub.entities;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
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
