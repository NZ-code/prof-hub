package pl.zenev.profhub.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
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
public class Course {
    @Id
    private UUID uuid;
    private String name;
    private Date startDate;
    private Domain domain;

    public Course(String name, Date startDate, Domain domain){
        this.name = name;
        this.startDate = startDate;
        this.domain = domain;
        this.uuid =  UUID.randomUUID();
    }
  @Singular
  @OneToMany(mappedBy = "course")
  private List<Lecture> lectures = new ArrayList<>();

}
