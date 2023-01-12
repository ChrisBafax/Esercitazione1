package it.java.course.esercitazione1.business;

import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.Role;
import it.java.course.esercitazione1.model.User;
import it.java.course.esercitazione1.payload.request.SignupRequest;

import java.util.List;

public interface UserBO {

    User createU(SignupRequest signUpRequest);

    List<User> getAll();

    User getByID(long id);

    String delete(long id);

    User update(long id, User userRequest);

    User createRole(long id, Role role);

    Course createCourse(long id, Course course);
}
