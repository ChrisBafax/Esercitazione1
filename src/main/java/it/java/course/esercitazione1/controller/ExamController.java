package it.java.course.esercitazione1.controller;

import it.java.course.esercitazione1.business.impl.ExamBOImpl;
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
    ExamBOImpl examBO;

    @Autowired
    ExamRepository examRepository;

    @Autowired
    CourseRepository courseRepository;

    @GetMapping("/exam")
    public ResponseEntity<List<Exam>> getExams() {
        return new ResponseEntity<>(examBO.getAll(),HttpStatus.OK);
    }

    @PostMapping("/exam/add")
    public ResponseEntity<Exam> createExam(@RequestBody Exam exam) {
        return new ResponseEntity<>(examBO.create(exam), HttpStatus.CREATED);
    }

    @GetMapping("/exam/grade/{grade}")
    public ResponseEntity<List<Exam>> getExamsByGrade(@PathVariable int grade) {
        return new ResponseEntity<>(examBO.getByGrade(grade), HttpStatus.OK);
    }

    @PostMapping("/exam/{examId}/course/{courseId}")
    public ResponseEntity<Exam> addExamToCourse(@PathVariable Long examId, @PathVariable long courseId) {
        return new ResponseEntity<>(examBO.addExamToCourse(examId, courseId), HttpStatus.CREATED);
    }
}
