package pl.zenev.profhub.models;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class LectureModel {
    private UUID id;
    private String name;
    private float lengthInMinutes;
    private LocalDateTime creationDateTime;
    private LocalDateTime updateDateTime;
}
