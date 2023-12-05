package pl.zenev.profhub.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.zenev.profhub.entity.VersionAndCreationDateAuditable;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode
@Entity
public class Lecture extends VersionAndCreationDateAuditable implements Serializable {
    @Id
    private UUID uuid;
    private float lengthInMinutes;
    private String name;

    @ManyToOne
    @JoinColumn(name = "course")
    private Course course;
    @ManyToOne
    @JoinColumn(name = "professor")
    private Professor professor;

    @PrePersist
    @Override
    public void updateCreationDateTime() {
        super.updateCreationDateTime();
    }

    @PreUpdate
    @Override
    public void updateUpdateDateTime() {
        super.updateUpdateDateTime();
    }

    public Lecture( String name, Course course, float lengthInMinutes, Professor professor) {
        this.name = name;
        this.course = course;
        this.lengthInMinutes = lengthInMinutes;
        this.professor = professor;
        this.uuid = UUID.randomUUID();
    }
}
