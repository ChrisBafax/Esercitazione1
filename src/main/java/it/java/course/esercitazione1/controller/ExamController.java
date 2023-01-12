package it.java.course.esercitazione1.controller;

import it.java.course.esercitazione1.business.impl.ExamBOImpl;
import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.model.Exam;
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

    @GetMapping("/exam")
    public ResponseEntity<List<Exam>> getExams() {
        return new ResponseEntity<>(examBO.getAll(), HttpStatus.OK);
    }

    @PostMapping("/exam/add")
    public ResponseEntity<Exam> createExam(@RequestBody Exam exam) {
        return new ResponseEntity<>(examBO.create(exam), HttpStatus.CREATED);
    }

    @GetMapping("/exam/grade/{grade}")
    public ResponseEntity<List<Exam>> getExamsByGrade(@PathVariable int grade) {
        List<Exam> exams;
        try {
            exams = examBO.getByGrade(grade);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("There is no course with the given grade.");
        }
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @PostMapping("/exam/{examId}/course/{courseId}")
    public ResponseEntity<Exam> addExamToCourse(@PathVariable Long examId, @PathVariable long courseId) {
        Exam exam;
        try {
            exam = examBO.addExamToCourse(examId, courseId);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Exam with id " + examId + " not found.");
        }
        return new ResponseEntity<>(exam, HttpStatus.CREATED);
    }
}
