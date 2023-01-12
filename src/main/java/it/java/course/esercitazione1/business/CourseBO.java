package it.java.course.esercitazione1.business;

import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.Exam;
import it.java.course.esercitazione1.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface CourseBO {

    Set<Course> getAll();

    Course getByID(long id);

    Course createC(Course course);

    String delete(long id);

    Course update(long id, Course courseRequest);

    List<User> getCourseUsers(long id);

    ArrayList<Exam> getCourseExams(long id);
}
