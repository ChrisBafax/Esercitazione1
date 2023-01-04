package it.java.course.esercitazione1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(max = 120)
    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.PERSIST
    }, mappedBy = "users", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Course> courses = new LinkedHashSet<>();

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.PERSIST
    },  fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new LinkedHashSet<>();
}
