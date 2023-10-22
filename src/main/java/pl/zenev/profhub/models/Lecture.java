package pl.zenev.profhub.models;

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
    private String professorName;

    private Course course;
    private Professor professor;

    public Lecture(float lengthInMinutes, String name, String professorName, Course course, Professor professor) {
        this.uuid = uuid;
        this.lengthInMinutes = lengthInMinutes;
        this.name = name;
        this.professorName = professorName;
        this.course = course;
        this.professor = professor;
        this.uuid = UUID.randomUUID();
    }
}
