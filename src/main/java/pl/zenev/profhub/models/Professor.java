package pl.zenev.profhub.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Professor {
    public Professor(String name, int age) {
        this.name = name;
        this.age = age;
        this.finishedLectures = new ArrayList<>();
        this.id = UUID.randomUUID();
    }
    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int age;
    @Getter
    @Setter
    private List<Lecture> finishedLectures;

}
