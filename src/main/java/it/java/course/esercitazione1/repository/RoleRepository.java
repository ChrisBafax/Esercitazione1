package it.java.course.esercitazione1.repository;

import it.java.course.esercitazione1.model.Role;
import it.java.course.esercitazione1.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleType(RoleType roleType);
}
