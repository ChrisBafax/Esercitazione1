package it.java.course.esercitazione1.business;

import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.Exam;
import it.java.course.esercitazione1.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface CourseBO {

    public Set<Course> getAll();

    public Course getByID(long id);

    public Course createC(Course course);

    public String delete(long id);

    public Course update(long id, Course courseRequest);

    public List<User> getCourseUsers(long id);

    public ArrayList<Exam> getCourseExams(long id);
}
