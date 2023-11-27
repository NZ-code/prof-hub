package pl.zenev.profhub.entities;

import jakarta.persistence.*;
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
    private String login;

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


    /**
     * User's password.
     */
    @ToString.Exclude
    private String password;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] picture;

    @CollectionTable(name = "users__roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

}
