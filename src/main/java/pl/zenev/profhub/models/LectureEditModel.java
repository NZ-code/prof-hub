package pl.zenev.profhub.models;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class LectureEditModel {
    private UUID id;
    private String name;
    private Long version;
    private float lengthInMinutes;
}
