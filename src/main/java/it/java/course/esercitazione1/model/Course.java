package it.java.course.esercitazione1.model;

// Import from FasterXML
import com.fasterxml.jackson.annotation.JsonIgnore;

// Import from Jakarta
import jakarta.persistence.*;

// Import from Lombok
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Import from Java
import java.util.*;

@Entity
@Table(name = "course")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.PERSIST
    }, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "courses_users",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new LinkedHashSet<>();

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    @OneToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.PERSIST
    }, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Exam> exams = new LinkedList<>();
}
