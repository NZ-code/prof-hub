package pl.zenev.profhub.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
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
    @ToString.Exclude//It's common to exclude lists from toString
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "professor")
    private List<Lecture> finishedLectures;








    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] picture;

}
