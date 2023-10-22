package pl.zenev.profhub.repositories;


import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.datasources.DataStorage;
import pl.zenev.profhub.models.Course;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@NoArgsConstructor(force = true)
public class CourseRepository implements Repository<Course>{
    private DataStorage dataStorage;
    @Inject
    public CourseRepository(DataStorage dataStorage){
        this.dataStorage = dataStorage;
    }

    @Override
    public List<Course> getAll() {
        return null;
    }

    @Override
    public Optional<Course> getById(UUID uuid) {
        return dataStorage.getCourseById(uuid);
    }

    @Override
    public void add(Course course) {
        dataStorage.addCourse(course);
    }


}
