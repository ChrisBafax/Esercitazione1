package it.java.course.esercitazione1.controller;

import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @GetMapping("/course")
    public ResponseEntity<List<Course>> getCourses() {
        List<Course> courseArrayList = new ArrayList<Course>();
        courseRepository.findAll().forEach(courseArrayList::add);
        if (courseArrayList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courseArrayList, HttpStatus.OK);
    }

    @PostMapping("/course/add")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course courseN = courseRepository.save(course);
        return new ResponseEntity<>(courseN, HttpStatus.CREATED);
    }

    @DeleteMapping("/course/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable("id") long id) {
        courseRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/course/update/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable("id") long id, @RequestBody Course courseRequest) {
        Course courseU = courseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Course ID " + id + " not found.")
        );

        // Check if the variable name has been updated or not
        if (courseRequest.getName() != null) {
            courseU.setName(courseRequest.getName());
        }

        // Check if the variable description has been updated or not
        if (courseRequest.getDescription() != null) {
            courseU.setDescription(courseRequest.getDescription());
        }

        return new ResponseEntity<>(courseRepository.save(courseU), HttpStatus.OK);

    }
}
