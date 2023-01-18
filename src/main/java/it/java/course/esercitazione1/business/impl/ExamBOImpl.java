package it.java.course.esercitazione1.business.impl;

import it.java.course.esercitazione1.business.CourseBO;
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
    CourseBO courseBO;

    public List<Exam> getAll() {
        return examRepository.findAll();
    }

    public Exam getById(Long id) throws ResourceNotFoundException {
        return examRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Exam not found."));
    }

    public Exam create(Exam exam) {
        return examRepository.save(exam);
    }

    public List<Exam> getByGrade(int grade) throws ResourceNotFoundException {
        List<Exam> exams = examRepository.findByGrade(grade);

        if (exams.isEmpty()) {
            throw new ResourceNotFoundException("Grade course.");
        } else {
            return exams;
        }
    }

    public Exam addExamToCourse(Long examId, long courseId) throws ResourceNotFoundException {
        Exam exam = getById(examId);
        Course course = courseBO.getByID(courseId);
        exam.setCourse(course);
        return examRepository.save(exam);
    }

}
