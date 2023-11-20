package pl.zenev.profhub.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Entity
public class Lecture {
    @Id
    private UUID uuid;
    private float lengthInMinutes;
    private String name;

    @ManyToOne
    private Course course;
    @ManyToOne
    private Professor professor;

    public Lecture( String name, Course course, float lengthInMinutes, Professor professor) {
        this.name = name;
        this.course = course;
        this.lengthInMinutes = lengthInMinutes;
        this.professor = professor;
        this.uuid = UUID.randomUUID();
    }
}
