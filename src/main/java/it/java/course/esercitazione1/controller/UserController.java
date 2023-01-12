package it.java.course.esercitazione1.controller;

// Import from other packages
import it.java.course.esercitazione1.business.impl.AuthBOImpl;
import it.java.course.esercitazione1.business.impl.UserBOImpl;

import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.Role;
import it.java.course.esercitazione1.model.User;

import it.java.course.esercitazione1.payload.request.SignupRequest;

// Import from SpringFrameWork
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

// Import from Java
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    AuthBOImpl authBO;
    @Autowired
    UserBOImpl userBO;

    @GetMapping("/user")
    // Show all the users
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(userBO.getAll(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    // Look for a user from his id
    public ResponseEntity<?> getUserByID(@PathVariable("id") long id) {
        return new ResponseEntity<>(userBO.getByID(id), HttpStatus.OK);
    }

    @PostMapping("/user/add")
    // Create a new user with role USER
    public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest signUpRequest) {
        User user = userBO.createU(signUpRequest);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/user/add/mod")
    // Create a new user with role option
    public ResponseEntity<?> createUserAdmin(@Valid @RequestBody SignupRequest signUpRequest) {
        User user = authBO.registerU(signUpRequest);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{id}/delete")
    // Delete an existing user id
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        return new ResponseEntity<>(userBO.delete(id), HttpStatus.OK);
    }

    @PutMapping("/user/{id}/update")
    // Update a user from his id
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User userRequest) {
        return new ResponseEntity<>(userBO.update(id, userRequest), HttpStatus.OK);
    }

    @GetMapping("/user/{id}/courses")
    // Look for all the courses a user is following
    public List<Course> getCoursesForUser(@PathVariable("id") long id) {
        User user = userBO.getByID(id);
        return user.getCourses();
    }

    @PostMapping("/user/{id}/role")
    // Set the role for a given user
    public ResponseEntity<?> createRoleUser(@PathVariable long id, @RequestBody Role role) {
        return new ResponseEntity<>(userBO.createRole(id, role), HttpStatus.CREATED);
    }

    @PostMapping("/user/{id}/course")
    // Add a user to a course
    public ResponseEntity<?> createCourseUser(@PathVariable long id, @RequestBody Course course) {
        return new ResponseEntity<>(userBO.createCourse(id, course), HttpStatus.CREATED);
    }
}