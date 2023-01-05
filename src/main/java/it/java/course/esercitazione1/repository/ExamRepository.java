package it.java.course.esercitazione1.repository;

import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByGrade(int grade);

    List<Exam> findByCourse(Course course);
}
