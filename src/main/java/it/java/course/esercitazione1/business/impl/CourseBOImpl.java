package it.java.course.esercitazione1.business.impl;

import it.java.course.esercitazione1.business.CourseBO;
import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.Exam;
import it.java.course.esercitazione1.model.User;
import it.java.course.esercitazione1.repository.CourseRepository;
import it.java.course.esercitazione1.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CourseBOImpl implements CourseBO {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ExamRepository examRepository;

    public Set<Course> getAll() {
        Set<Course> courseArrayList = new HashSet<>(courseRepository.findAll());

        if (courseArrayList.isEmpty()) {
            throw new ResourceNotFoundException("The Course Database is empty at this moment.");
        } else {
            return courseArrayList;
        }
    }

    public Course getByID(long id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The course ID " + id +
                        " you are looking for does not exist in this database.")
        );

        return course;
    }

    public Course createC(Course course) {
        Course courseN = courseRepository.save(course);
        return courseN;
    }

    public String delete(long id) {
        // Check existence of the Course
        getByID(id);
        courseRepository.deleteById(id);
        return "The course with the ID " + id +
                " has been successfully been deleted";
    }

    public Course update(long id, Course courseRequest) {
        Course courseU = getByID(id);

        // Check if the variable name has been updated or not
        if (courseRequest.getName() != null) {
            courseU.setName(courseRequest.getName());
        }

        // Check if the variable description has been updated or not
        if (courseRequest.getDescription() != null) {
            courseU.setDescription(courseRequest.getDescription());
        }
        courseRepository.save(courseU);
        return courseU;
    }

    public List<User> getCourseUsers(long id) {
        Course course = getByID(id);
        List<User> user= course.getUsers();
        return user;
    }

    public ArrayList<Exam> getCourseExams(long id) {
        Course course = getByID(id);
        ArrayList<Exam> examL = (ArrayList<Exam>) examRepository.findByCourse(course);
        return examL;
    }
}
