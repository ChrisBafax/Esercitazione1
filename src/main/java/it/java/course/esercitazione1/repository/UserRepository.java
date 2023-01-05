package it.java.course.esercitazione1.repository;

import it.java.course.esercitazione1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
