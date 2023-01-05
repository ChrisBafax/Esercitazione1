package it.java.course.esercitazione1.controller;

import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.Exam;
import it.java.course.esercitazione1.repository.CourseRepository;
import it.java.course.esercitazione1.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExamController {

    @Autowired
    ExamRepository examRepository;

    @Autowired
    CourseRepository courseRepository;

    @GetMapping("/exam")
    public List<Exam> getExams() {
        return examRepository.findAll();
    }

    @PostMapping("/exam/add")
    public ResponseEntity<Exam> createExam(@RequestBody Exam exam) {
        Exam examA = examRepository.save(exam);
        return new ResponseEntity<>(examA, HttpStatus.CREATED);
    }

    @GetMapping("/exam/grade/{grade}")
    public ResponseEntity<?> getExamsByGrade(@PathVariable int grade) {
        if (examRepository.findByGrade(grade).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(examRepository.findByGrade(grade), HttpStatus.OK);
    }

    @PostMapping("/exam/{examId}/course/{courseId}")
    public Exam addExamToCourse(@PathVariable Long examId, @PathVariable Long courseId) {
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new ResourceNotFoundException("Exam not found with id " + examId));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));
        exam.setCourse(course);
        return examRepository.save(exam);
    }
}
