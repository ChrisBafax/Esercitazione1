package it.java.course.esercitazione1.business;

import it.java.course.esercitazione1.exception.ResourceAlreadyPresentException;
import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.Role;
import it.java.course.esercitazione1.model.User;
import it.java.course.esercitazione1.payload.request.SignupRequest;

import java.util.List;

public interface UserBO {

    User createU(SignupRequest signUpRequest) throws ResourceAlreadyPresentException, ResourceNotFoundException;

    List<User> getAll() throws ResourceNotFoundException;

    User getByID(long id) throws ResourceNotFoundException;

    String delete(long id) throws ResourceNotFoundException;

    User update(long id, User userRequest) throws ResourceNotFoundException;

    User createRole(long id, Role role);

    Course createCourse(long id, Course course);
}
