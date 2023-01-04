package it.java.course.esercitazione1.repository;

import it.java.course.esercitazione1.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
