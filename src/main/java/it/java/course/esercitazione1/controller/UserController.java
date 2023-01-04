package it.java.course.esercitazione1.controller;

import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.Role;
import it.java.course.esercitazione1.model.User;
import it.java.course.esercitazione1.repository.CourseRepository;
import it.java.course.esercitazione1.repository.RoleRepository;
import it.java.course.esercitazione1.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/user")
    // Show all the users
    public ResponseEntity<?> getUsers() {
        List<User> userArrayList = new ArrayList<>(userRepository.findAll());

        if (userArrayList.isEmpty()) {
            return new ResponseEntity<>("The Course Database is empty at this moment.",
                    HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(userArrayList, HttpStatus.OK);
        }
    }

    @GetMapping("/user/{id}")
    // Look for a user from his id
    public ResponseEntity<?> getUserByID(@PathVariable("id") long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            // Custom output message
            return new ResponseEntity<>("The user ID " + id +
                    " you are looking for does not exist in this database.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user/add")
    // Create a new user
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User userN = userRepository.save(user);

        return new ResponseEntity<>(userN, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/delete/{id}")
    // Delete an existing user id
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User ID " + id + " not found.")
        );

        userRepository.deleteById(id);

        // Custom output message
        return new ResponseEntity<>("The course with the ID " + id +
                " has been successfully been deleted", HttpStatus.OK);
    }

    @PutMapping("/user/update/{id}")
    // Update a user from his id
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User userRequest) {
        User userU = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User ID " + id +
                        " not found")
        );

        // Check if the variable username has been updated or not
        if (userRequest.getUsername() != null) {
            userU.setUsername(userRequest.getUsername());
        }

        // Check if the variable email has been updated or not
        if (userRequest.getEmail() != null) {
            userU.setEmail(userRequest.getEmail());
        }

        // Check if the variable password has been updated or not
        if (userRequest.getPassword() != null) {
            userU.setPassword(userRequest.getPassword());
        }

        return new ResponseEntity<>(userRepository.save(userU), HttpStatus.OK);
    }

    @GetMapping("/user/{id}/courses")
    // Look for all the courses a user is following
    public List<Course> getCoursesForUser(@PathVariable("id") long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with ID " + id + " not found.")
        );
        return user.getCourses();
    }

    @PostMapping("/user/{id}/role")
    // Set the role for a given user
    public ResponseEntity<?> createRoleUser(@PathVariable Long id, @RequestBody Role role) {
        User userU = userRepository.getReferenceById(id);

        Role roleU = roleRepository.save(role);

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleU);
        userU.setRoles(roleSet);
        User userA = userRepository.save(userU);

        return new ResponseEntity<>(userA,HttpStatus.CREATED);
    }

}
