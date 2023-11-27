package pl.zenev.profhub.services;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.entities.Lecture;
import pl.zenev.profhub.repositories.CourseRepository;
import pl.zenev.profhub.repositories.LectureRepository;
import pl.zenev.profhub.repositories.ProfessorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class LectureService implements Service<Lecture>{
    final LectureRepository lectureRepository;
    final CourseRepository courseRepository;
    @Inject
    public LectureService(LectureRepository lectureRepository, CourseRepository courseRepository) {
        this.lectureRepository = lectureRepository;
        this.courseRepository = courseRepository;
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
    //@Transactional
    public void add(Lecture lecture) {
        lectureRepository.add(lecture);
    }

    public Optional<List<Lecture>> getAllByCourseId(UUID uuid) {
        System.out.println("course :" + courseRepository.getById(uuid));
        Optional<Course> course = courseRepository.getById(uuid);
        if(course.isEmpty()){
            return Optional.empty();
        }
        System.out.println("lectureRepository.getLecturesByCourseId(uuid)="+ lectureRepository.getLecturesByCourseId(uuid));
        return Optional.ofNullable(lectureRepository.getLecturesByCourseId(uuid));
    }
    //@Transactional
    public void delete(UUID id) {
        lectureRepository.delete(id);
    }
    //@Transactional
    public void update(Lecture lecture) {
        lectureRepository.update(lecture);
    }
}
