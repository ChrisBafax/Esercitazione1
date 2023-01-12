package it.java.course.esercitazione1.business;

import it.java.course.esercitazione1.model.Exam;

import java.util.List;

public interface ExamBO {

    List<Exam> getAll();

    Exam getById(Long id);

    Exam create(Exam exam);

    List<Exam> getByGrade(int grade);

    Exam addExamToCourse(Long examId, long courseId);
}
