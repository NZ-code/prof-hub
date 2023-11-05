package pl.zenev.profhub.models;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class CourseModel {
    private String name;
    private Date startDate;
    private UUID uuid;

}
