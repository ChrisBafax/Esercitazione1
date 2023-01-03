package it.java.course.esercitazione1.controller;

import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getCourses() {
        List<Course> courseArrayList = new ArrayList<Course>();
        courseRepository.findAll().forEach(courseArrayList::add);
        if (courseArrayList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courseArrayList, HttpStatus.OK);
    }

    @PostMapping("/courseAdd")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course courseN = courseRepository.save(course);
        return new ResponseEntity<>(courseN, HttpStatus.CREATED);
    }

    @DeleteMapping("/course/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable("id") long id) {
        courseRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
