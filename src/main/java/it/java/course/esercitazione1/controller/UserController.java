package it.java.course.esercitazione1.controller;

import it.java.course.esercitazione1.business.AuthBO;
import it.java.course.esercitazione1.business.UserBO;
import it.java.course.esercitazione1.exception.ResourceAlreadyPresentException;
import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.Role;
import it.java.course.esercitazione1.model.User;
import it.java.course.esercitazione1.payload.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    AuthBO authBO;
    @Autowired
    UserBO userBO;

    @GetMapping("/user")
    // Show all the users
    public ResponseEntity<List<User>> getUsers() {
        List<User> users;
        try {
            users = userBO.getAll();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("The User Database is empty at this moment.");
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    // Look for a user from his id
    public ResponseEntity<User> getUserByID(@PathVariable("id") long id) {
        User user;
        try {
            user = userBO.getByID(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("The user ID " + id +
                    " you are looking for does not exist.");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/user/add")
    // Create a new user with role USER
    public ResponseEntity<User> createUser(@Valid @RequestBody SignupRequest signUpRequest) {
        User user;
        try {
            user = userBO.createU(signUpRequest);
        } catch (ResourceAlreadyPresentException e) {
            throw new ResourceAlreadyPresentException("Error: Username/Email is already in use!");
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Error: Role is not found.");
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/user/add/mod")
    // Create a new user with role option
    public ResponseEntity<User> createUserAdmin(@Valid @RequestBody SignupRequest signUpRequest) {
        User user;
        try {
            user = userBO.createUMod(signUpRequest);
        } catch (ResourceAlreadyPresentException e) {
            throw new ResourceAlreadyPresentException("Error: Username/Email is already in use!");
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Error: Role is not found.");
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{id}/delete")
    // Delete an existing user id
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        String strg;
        try {
            strg = userBO.delete(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("The user ID " + id +
                    " you are trying to delete does not exist.");
        }
        return new ResponseEntity<>(strg, HttpStatus.OK);
    }

    @PutMapping("/user/{id}/update")
    // Update a user from his id
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User userRequest) {
        User userU;
        try {
            userU = userBO.update(id, userRequest);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("The user ID " + id +
                    " you are looking to update " +
                    "does not exist in this database.");
        }
        return new ResponseEntity<>(userU, HttpStatus.OK);
    }

    @GetMapping("/user/{id}/courses")
    // Look for all the courses a user is following
    public ResponseEntity<List<Course>> getCoursesForUser(@PathVariable("id") long id) {
        List<Course> user;
        try {
            user = userBO.getCourses(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("The user ID " + id +
                    " you are looking the courses for does not exist.");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/user/{id}/role")
    // Set the role for a given user
    public ResponseEntity<User> createRoleUser(@PathVariable long id, @RequestBody Role role) {
        return new ResponseEntity<>(userBO.createRole(id, role), HttpStatus.CREATED);
    }

    @PostMapping("/user/{id}/course")
    // Add a user to a course
    public ResponseEntity<Course> createCourseUser(@PathVariable long id, @RequestBody Course course) {
        return new ResponseEntity<>(userBO.createCourse(id, course), HttpStatus.CREATED);
    }
}