package it.java.course.esercitazione1.business;

import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.Exam;
import it.java.course.esercitazione1.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface CourseBO {

    Set<Course> getAll() throws ResourceNotFoundException;

    Course getByID(long id) throws ResourceNotFoundException;

    Course createC(Course course);

    String delete(long id) throws ResourceNotFoundException;

    Course update(long id, Course courseRequest) throws ResourceNotFoundException;

    List<User> getCourseUsers(long id) throws ResourceNotFoundException;

    ArrayList<Exam> getCourseExams(long id) throws ResourceNotFoundException;

    void uploadFile(Long id, MultipartFile data) throws IOException;

    Course findByIdFile(Long id);
}
