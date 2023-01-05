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
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private long id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.PERSIST
    }, mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users = new LinkedHashSet<>();
    public List<User> getUsers() {
        return new ArrayList<>(users);
    }
}
