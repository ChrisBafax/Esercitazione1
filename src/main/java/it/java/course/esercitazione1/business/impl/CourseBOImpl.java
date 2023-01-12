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

    public Set<Course> getAll() throws ResourceNotFoundException {
        Set<Course> courseArrayList = new HashSet<>(courseRepository.findAll());

        if (courseArrayList.isEmpty()) {
            throw new ResourceNotFoundException("Database empty.");
        } else {
            return courseArrayList;
        }
    }

    public Course getByID(long id) throws ResourceNotFoundException {

        return courseRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Course ID."));
    }

    public Course createC(Course course) {
        return courseRepository.save(course);
    }

    public String delete(long id) throws ResourceNotFoundException {
        // Check existence of the Course
        getByID(id);
        courseRepository.deleteById(id);
        return "The course with the ID " + id +
                " has been successfully been deleted";
    }

    public Course update(long id, Course courseRequest) throws ResourceNotFoundException {
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

    public List<User> getCourseUsers(long id) throws ResourceNotFoundException {
        Course course = getByID(id);
        return course.getUsers();
    }

    public ArrayList<Exam> getCourseExams(long id) throws ResourceNotFoundException {
        Course course = getByID(id);
        return (ArrayList<Exam>) examRepository.findByCourse(course);
    }
}
