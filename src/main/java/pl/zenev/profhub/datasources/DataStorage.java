package pl.zenev.profhub.datasources;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.zenev.profhub.models.Course;
import pl.zenev.profhub.models.Domain;
import pl.zenev.profhub.models.Lecture;
import pl.zenev.profhub.models.Professor;

import java.util.*;

@Getter
@ApplicationScoped

public class DataStorage {
    private List<Professor> professors = new ArrayList<>(
    );
    private List<Course> courses = new ArrayList<>(
    );
    private List<Lecture> lectures = new ArrayList<>(
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

    public void addLecture(Lecture lecture) {
        this.lectures.add(lecture);
    }

    public Optional<Lecture> getLecture(UUID uuid) {
        return lectures.stream().filter(lecture -> lecture.getUuid().equals(uuid)).findFirst();
    }
}
