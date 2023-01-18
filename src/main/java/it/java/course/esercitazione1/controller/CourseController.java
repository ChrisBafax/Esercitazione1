package it.java.course.esercitazione1.controller;

import it.java.course.esercitazione1.business.CourseBO;
import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.Exam;
import it.java.course.esercitazione1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CourseBO courseBO;

    @GetMapping("/course")
    // Show all the courses saved in the database
    public ResponseEntity<Set<Course>> getCourses() {
        return new ResponseEntity<>(courseBO.getAll(), HttpStatus.OK);
    }

    @GetMapping("/course/{id}")
    // Show a course by giving his id
    public ResponseEntity<Course> getCourseByID(@PathVariable("id") long id) {
        Course courseID;
        try {
            courseID = courseBO.getByID(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("The course with the ID " + id + " has not been found.");
        }
        return new ResponseEntity<>(courseID, HttpStatus.OK);
    }

    @PostMapping("/course/add")
    // Add a new course to the db
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return new ResponseEntity<>(courseBO.createC(course), HttpStatus.CREATED);
    }

    @DeleteMapping("/course/{id}/delete")
    // Delete a course by his id
    public ResponseEntity<String> deleteCourse(@PathVariable("id") long id) {
        String courseD;
        try {
            courseD = courseBO.delete(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("The course with the ID " + id + " that you are trying to delete doesn't exist.");
        }
        return new ResponseEntity<>(courseD, HttpStatus.OK);
    }

    @PutMapping("/course/{id}/update")
    // Update a course by his id
    public ResponseEntity<Course> updateCourse(@PathVariable("id") long id, @RequestBody Course courseRequest) {
        Course courseU;
        try {
            courseU = courseBO.update(id, courseRequest);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("The course with the ID " + id + " that you are trying to update doesn't exist.");
        }
        return new ResponseEntity<>(courseU, HttpStatus.OK);
    }

    @GetMapping("/course/{id}/users")
    // Look for the users that are in the given course
    public ResponseEntity<List<User>> getUsersForCourse(@PathVariable("id") long id) {
        List<User> users;
        try {
            users = courseBO.getCourseUsers(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("The course with the ID " + id + " that you are trying to reach doesn't exist.");
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/course/{id}/exams")
    public ResponseEntity<ArrayList<Exam>> getExamsByCourse(@PathVariable("id") long id) {
        ArrayList<Exam> exams;
        try {
            exams = courseBO.getCourseExams(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("The course with the ID " + id + " that you are trying to reach doesn't exist.");
        }
        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @PostMapping("/course/{id}/file/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile data) {
        try {
            courseBO.uploadFile(id, data);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> map = new HashMap<>();
            String message = "Non posso caricare il file: " + data.getOriginalFilename();
            map.put("Error", message);
            return new ResponseEntity<>(map, HttpStatus.EXPECTATION_FAILED);

        }
    }

    @GetMapping("/course/{id}/file")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        Course course = courseBO.findByIdFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + course.getName() + "\"")
                .body(course.getData());
    }

    @PutMapping("/course/{id}/file/delete")
    public ResponseEntity<Course> deleteFile(@PathVariable Long id) throws IOException {
        Course course = courseBO.deleteFileById(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }
}