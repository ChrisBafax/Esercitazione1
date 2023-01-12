package it.java.course.esercitazione1.business.impl;

import it.java.course.esercitazione1.business.ExamBO;
import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.Exam;
import it.java.course.esercitazione1.repository.CourseRepository;
import it.java.course.esercitazione1.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamBOImpl implements ExamBO {

    @Autowired
    ExamRepository examRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseBOImpl courseBO;

    public List<Exam> getAll() {
        List<Exam> exams = examRepository.findAll();
        return exams;
    }

    public Exam getById(Long id) {
        Exam exam = examRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Exam not found with id " + id));
        return exam;
    }

    public Exam create(Exam exam) {
        Exam examA = examRepository.save(exam);
        return examA;
    }

    public List<Exam> getByGrade(int grade) {
        List<Exam> exams = examRepository.findByGrade(grade);

        if (exams.isEmpty()) {
            throw new ResourceNotFoundException("There is no course with the given grade");
        } else {
            return exams;
        }
    }

    public Exam addExamToCourse(Long examId, long courseId) {
        Exam exam = getById(examId);
        Course course = courseBO.getByID(courseId);
        exam.setCourse(course);
        Exam examS = examRepository.save(exam);
        return examS;
    }

}
