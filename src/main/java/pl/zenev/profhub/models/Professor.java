package pl.zenev.profhub.models;

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
public class Professor {
    public Professor(String name, int age, UUID uuid) {
        this.name = name;
        this.age = age;
        this.id = uuid;
    }
    public Professor(String name, int age) {
        this.name = name;
        this.age = age;
        this.finishedLectures = new ArrayList<>();
        this.id = UUID.randomUUID();
    }

    private UUID id;


    private String name;

    private int age;

    private List<Lecture> finishedLectures;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] picture;

}
