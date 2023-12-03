package pl.zenev.profhub.repositories;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.BadRequestException;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.datasources.DataStorage;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.entities.Lecture;
import pl.zenev.profhub.entities.Professor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Dependent
//@RequestScoped
//@NoArgsConstructor(force = true)
public class LectureRepository implements Repository<Lecture>{

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }
//    private DataStorage dataStorage;
//    @Inject
//    public LectureRepository(DataStorage dataStorage) {
//        this.dataStorage = dataStorage;
//    }
    @Override
    public List<Lecture> getAll() {
        return em.createQuery("select p from Lecture p", Lecture.class).getResultList();
        //return dataStorage.getLectures();
    }

    @Override
    public Optional<Lecture> getById(UUID uuid) {
        return Optional.ofNullable(em.find(Lecture.class, uuid));
        //return dataStorage.getLecture(uuid);
    }

    @Override
    public void add(Lecture lecture) {
        try{
            em.persist(lecture);

        }
        catch (Exception exception){
            throw new BadRequestException(exception);
        }
        //dataStorage.addLecture(lecture);
    }

    public List<Lecture> getLecturesByCourse(Course course) {
        return getAll().stream().filter(lecture -> lecture.getCourse().equals(course)).toList();
    }

    public List<Lecture> getLecturesByCourseId(UUID uuid) {

        return getAll().stream().filter(lecture -> lecture.getCourse().getUuid().equals(uuid)).toList();
    }

    public void delete(UUID id) {
        em.remove(em.find(Lecture.class, id));
        //dataStorage.deleteLecture(id);
    }

    public void deleteByCourseId(UUID id) {

        //dataStorage.deleteLecturesByCourse(id);
    }

    public void update(Lecture lecture) {
//        dataStorage.updateLecture(lecture);
        em.merge(lecture);
    }

    public Optional<Lecture> findByIdAndUser(UUID id, Professor professor) {
        try {
            return Optional.of(em
                    .createQuery("select l from Lecture l where l.uuid = :id and l.professor = :professor", Lecture.class)
                    .setParameter("professor", professor)
                    .setParameter("id", id)
                    .getSingleResult());
        }
        catch (NoResultException ex){
            return Optional.empty();
        }
    }


    public List<Lecture> findByUser(Professor professor) {
        return em
                .createQuery("select l from Lecture l where l.professor = :professor", Lecture.class)
                .setParameter("professor", professor)
                .getResultList();
    }
}
