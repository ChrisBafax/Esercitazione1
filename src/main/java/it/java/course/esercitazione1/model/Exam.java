package it.java.course.esercitazione1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "exam")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "grade")
    @Size(max = 2)
    private int grade;

    @Column(name = "day")
    @Size(max = 2)
    private int day;

    @Column(name = "month")
    @Size(max = 2)
    private int month;

    @Column(name = "year")
    @Size(max = 4)
    private int year;

    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.PERSIST
    }, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "exam_courses",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Course course;

    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.PERSIST
    }, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "exam_user",
        joinColumns = @JoinColumn(name = "exam_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new LinkedList<>();

    public void setCourse(Course course) {
        this.course = course;
    }
}
