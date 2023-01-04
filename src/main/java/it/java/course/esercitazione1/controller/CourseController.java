package it.java.course.esercitazione1.controller;

import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.User;
import it.java.course.esercitazione1.repository.CourseRepository;

import it.java.course.esercitazione1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/course")
    // Show all the courses saved in the database
    public ResponseEntity<?> getCourses() {
        List<Course> courseArrayList = new ArrayList<>(courseRepository.findAll());

        if (courseArrayList.isEmpty()) {
            return new ResponseEntity<>("The Course Database is empty at this moment.",
                    HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(courseArrayList, HttpStatus.OK);
        }
    }

    @GetMapping("/course/{id}")
    // Show a course by giving his id
    public ResponseEntity<?> getCourseByID(@PathVariable("id") long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            return new ResponseEntity<>(course, HttpStatus.OK);
        } else {
            // Custom output message
            return new ResponseEntity<>("The course ID " + id +
                    " you are looking for does not exist in this database.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/course/add")
    // Add a new course to the db
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course courseN = courseRepository.save(course);

        return new ResponseEntity<>(courseN, HttpStatus.CREATED);
    }

    @DeleteMapping("/course/delete/{id}")
    // Delete a course by his id
    public ResponseEntity<String> deleteCourse(@PathVariable("id") long id) {
        // Check existence of the Course
        courseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Course ID " + id + " not found.")
        );

        courseRepository.deleteById(id);

        // Custom output message
        return new ResponseEntity<>("The course with the ID " + id +
                " has been successfully been deleted", HttpStatus.OK);
    }

    @PutMapping("/course/update/{id}")
    // Update a course by his id
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

    @PostMapping("/user/{id}/course")
    public ResponseEntity<?> createCourseUser(@PathVariable Long id, @RequestBody Course course) {
        User user = userRepository.getReferenceById(id);

        Course courseU = courseRepository.getReferenceById(course.getId());

        Set<User> userSet = new HashSet<>();
        userSet.add(user);
        courseU.setUsers(userSet);
        Course courseA = courseRepository.save(courseU);
        return new ResponseEntity<>(courseA,HttpStatus.CREATED);
    }
}
