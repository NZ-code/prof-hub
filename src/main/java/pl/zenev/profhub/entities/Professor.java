package pl.zenev.profhub.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Entity
public class Professor {
    @Id
    private UUID id;


    private String name;

    private int age;

    @OneToMany(mappedBy = "professor")
    private List<Lecture> finishedLectures;
    public Professor(String name, int age) {
        UUID id = UUID.randomUUID();
        this.name = name;
        this.age = age;
    }

    public Professor(String name, int age, UUID uuid) {
        this(name, age);
        this.finishedLectures = new ArrayList<>();

        this.id = uuid;


    }



    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] picture;

}
