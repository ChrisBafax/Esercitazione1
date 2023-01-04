package it.java.course.esercitazione1.repository;

import it.java.course.esercitazione1.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
