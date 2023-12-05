package pl.zenev.profhub.dto;

import lombok.*;

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
public class GetLecturesResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Lecture {
        private UUID id;
        private String name;
        private String professor;
        private Long version;
        private LocalDateTime creationDateTime;
        private LocalDateTime updateDateTime;
    }

    @Singular
    private List<Lecture> lectures;
}
