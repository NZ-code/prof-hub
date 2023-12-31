package pl.zenev.profhub.datasources;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import pl.zenev.profhub.entities.Course;
import pl.zenev.profhub.entities.Lecture;
import pl.zenev.profhub.entities.Professor;

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

    public void deleteCourse(UUID id) {
        courses.removeIf(course -> course.getUuid().equals(id));
    }

    public void deleteLecture(UUID id) {
        lectures.removeIf(lecture -> lecture.getUuid().equals(id));
    }
}
