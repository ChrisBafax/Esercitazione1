package it.java.course.esercitazione1.business.impl;

import it.java.course.esercitazione1.business.UserBO;
import it.java.course.esercitazione1.exception.ResourceAlreadyPresentException;
import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.Role;
import it.java.course.esercitazione1.model.User;
import it.java.course.esercitazione1.payload.request.SignupRequest;
import it.java.course.esercitazione1.repository.CourseRepository;
import it.java.course.esercitazione1.repository.RoleRepository;
import it.java.course.esercitazione1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserBOImpl implements UserBO {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    AuthBOImpl authBO;
    @Autowired
    PasswordEncoder encoder;

    public User createU(SignupRequest signUpRequest) throws ResourceAlreadyPresentException, ResourceNotFoundException {
        signUpRequest.setRole(null);
        return authBO.registerU(signUpRequest);
    }

    public List<User> getAll() throws ResourceNotFoundException {
        List<User> userArrayList = new ArrayList<>(userRepository.findAll());

        if (userArrayList.isEmpty()) {
            throw new ResourceNotFoundException("User Database.");
        } else {
            return userArrayList;
        }
    }

    public User getByID(long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User id.")
        );
        return user;
    }

    public String delete(long id) throws ResourceNotFoundException {
        User user = getByID(id);
        userRepository.deleteById(id);
        return "The course with the ID " + id +
                " has been successfully been deleted";
    }

    public User update(long id, User userRequest) throws ResourceNotFoundException {
        User userU = getByID(id);

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
        } else {
            userU.setPassword(encoder.encode(userRequest.getPassword()));
        }
        userRepository.save(userU);
        return userU;
    }

    public User createRole(long id, Role role) {
        User userU = userRepository.getReferenceById(id);
        Role roleU = roleRepository.save(role);

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleU);
        userU.setRoles(roleSet);

        return userRepository.save(userU);
    }

    public Course createCourse(long id, Course course) {
        User user = userRepository.getReferenceById(id);
        Course courseU = courseRepository.getReferenceById(course.getId());

        Set<User> userSet = new HashSet<>();
        userSet.add(user);
        courseU.setUsers(userSet);

        return courseRepository.save(courseU);
    }

}
