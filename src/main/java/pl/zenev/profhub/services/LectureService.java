package pl.zenev.profhub.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.models.Course;
import pl.zenev.profhub.models.Lecture;
import pl.zenev.profhub.repositories.CourseRepository;
import pl.zenev.profhub.repositories.LectureRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ApplicationScoped
@NoArgsConstructor(force = true)
public class LectureService implements Service<Lecture>{
    LectureRepository lectureRepository;
    @Inject
    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @Override
    public List<Lecture> getAll() {
        return lectureRepository.getAll();
    }

    @Override
    public Optional<Lecture> getById(UUID uuid) {
        return lectureRepository.getById(uuid);
    }

    @Override
    public void add(Lecture lecture) {
        lectureRepository.add(lecture);
    }
}
