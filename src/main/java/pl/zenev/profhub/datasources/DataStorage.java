package pl.zenev.profhub.datasources;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.models.Course;
import pl.zenev.profhub.models.Domain;
import pl.zenev.profhub.models.Professor;

import java.util.*;

@ApplicationScoped

public class DataStorage {
    @Getter
    private List<Professor> professors = new ArrayList<>(
    );
    @Getter
    private List<Course> courses = new ArrayList<>(
    );
    public DataStorage(){
        // professors

    }

    public void addProfessor(Professor professor) {
        professors.add(professor);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public Optional<Course> getCourseById(UUID uuid) {
        return courses.stream().filter(course -> course.getUuid().equals(uuid)).findFirst();
    }

    public Optional<Professor> getProfessorById(UUID uuid) {
        return professors.stream().filter(professor -> professor.getId().equals(uuid)).findFirst();
    }
}
