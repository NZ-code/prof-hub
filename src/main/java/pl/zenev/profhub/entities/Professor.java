package pl.zenev.profhub.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public Professor(String name, int age) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.age = age;
        this.finishedLectures = new ArrayList<>();
    }

    @OneToMany(mappedBy = "professor")
    private List<Lecture> finishedLectures;








    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] picture;

}
