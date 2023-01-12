package it.java.course.esercitazione1.business;

import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.Role;
import it.java.course.esercitazione1.model.User;
import it.java.course.esercitazione1.payload.request.SignupRequest;

import java.util.List;

public interface UserBO {

    public User createU(SignupRequest signUpRequest);

    public List<User> getAll();

    public User getByID(long id);

    public String delete(long id);

    public User update(long id, User userRequest);

    public User createRole(long id, Role role);

    public Course createCourse(long id, Course course);
}
