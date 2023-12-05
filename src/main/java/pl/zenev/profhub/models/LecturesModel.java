package pl.zenev.profhub.models;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class LecturesModel  {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Lecture{
        private UUID id;
        private float lengthInMinutes;
        private String name;
        private LocalDateTime creationDateTime;
        private LocalDateTime updateDateTime;
    }

    @Singular
    private List<Lecture> lectures;
}