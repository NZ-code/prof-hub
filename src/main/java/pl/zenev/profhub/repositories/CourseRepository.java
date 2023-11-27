package pl.zenev.profhub.repositories;


import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.datasources.DataStorage;
import pl.zenev.profhub.entities.Course;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
//@RequestScoped
//@NoArgsConstructor(force = true)
public class CourseRepository implements Repository<Course>{


    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    //private DataStorage dataStorage;
//    @Inject
//    public CourseRepository(DataStorage dataStorage){
//        this.dataStorage = dataStorage;
//    }

    @Override
    public List<Course> getAll() {
        return em.createQuery("select c from Course c", Course.class).getResultList();
    }

    @Override
    public Optional<Course> getById(UUID uuid) {
        return Optional.ofNullable(em.find(Course.class, uuid));
//        return dataStorage.getCourseById(uuid);
    }

    @Override
    public void add(Course course) {
        em.persist(course);
//        dataStorage.addCourse(course);
    }


    public void delete(Course course) {
        em.remove(em.find(Course.class, course.getUuid()));
        //dataStorage.deleteCourse(id);
    }

    public void update(Course course) {
        em.merge(course);
//        dataStorage.updateCourse(course);
    }
}
