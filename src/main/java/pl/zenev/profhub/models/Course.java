package pl.zenev.profhub.models;

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

public class Course {

    private String name;
    private Date startDate;
    private Domain domain;
    private UUID uuid;
    public Course(String name, Date startDate, Domain domain){
        this.name = name;
        this.startDate = startDate;
        this.domain = domain;
        this.uuid =  UUID.randomUUID();
    }

    @Singular
    private List<Lecture> lectures = new ArrayList<>();

}
