package it.java.course.esercitazione1.controller;

// Import from other packages
import it.java.course.esercitazione1.business.impl.CourseBOImpl;

import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.User;

// Import from SpringFrameWork
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

// Import from Java
import java.util.*;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseBOImpl courseBO;

    @GetMapping("/course")
    // Show all the courses saved in the database
    public ResponseEntity<?> getCourses() {
        return new ResponseEntity<>(courseBO.getAll(), HttpStatus.OK);
    }

    @GetMapping("/course/{id}")
    // Show a course by giving his id
    public ResponseEntity<?> getCourseByID(@PathVariable("id") long id) {
        return new ResponseEntity<>(courseBO.getByID(id), HttpStatus.OK);
    }

    @PostMapping("/course/add")
    // Add a new course to the db
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return new ResponseEntity<>(courseBO.createC(course), HttpStatus.CREATED);
    }

    @DeleteMapping("/course/{id}/delete")
    // Delete a course by his id
    public ResponseEntity<String> deleteCourse(@PathVariable("id") long id) {
        return new ResponseEntity<>(courseBO.delete(id), HttpStatus.OK);
    }

    @PutMapping("/course/{id}/update")
    // Update a course by his id
    public ResponseEntity<Course> updateCourse(@PathVariable("id") long id, @RequestBody Course courseRequest) {
        return new ResponseEntity<>(courseBO.update(id,courseRequest), HttpStatus.OK);
    }

    @GetMapping("/course/{id}/users")
    // Look for the users that are in the given course
    public List<User> getUsersForCourse(@PathVariable("id") long id) {
        return courseBO.getCourseUsers(id);
    }

    @GetMapping("/course/{id}/exams")
    public ResponseEntity<?> getExamsByCourse(@PathVariable("id") long id) {
        return new ResponseEntity<>(courseBO.getCourseExams(id), HttpStatus.OK);
    }
}