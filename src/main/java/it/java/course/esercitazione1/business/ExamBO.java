package it.java.course.esercitazione1.business;

import it.java.course.esercitazione1.model.Exam;

import java.util.List;

public interface ExamBO {

    public List<Exam> getAll();

    public Exam getById(Long id);

    public Exam create(Exam exam);

    public List<Exam> getByGrade(int grade);

    public Exam addExamToCourse(Long examId, long courseId);
}
