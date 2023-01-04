package it.java.course.esercitazione1.controller;

import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.model.Course;
import it.java.course.esercitazione1.model.Role;
import it.java.course.esercitazione1.model.RoleType;
import it.java.course.esercitazione1.model.User;
import it.java.course.esercitazione1.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/roles")
    public ResponseEntity<ArrayList<Role>> getRoles(){
        ArrayList<Role> _roles = (ArrayList<Role>) roleRepository.findAll();
        return new ResponseEntity<>(_roles, HttpStatus.OK);
    }

    @PostMapping("/role/Add")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        Role _role = roleRepository.save(role);
        return new ResponseEntity<Role>(_role,HttpStatus.OK);
    }
    @DeleteMapping("/role/{id}")
    public ResponseEntity<HttpStatus> deleterole(@PathVariable long id){
        roleRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/role/{roleType}/users")
    public List<User> getUsersForCourse(@PathVariable("roleType") RoleType roleType) {
        Role role = roleRepository.findByroleType(roleType).orElseThrow(
                () -> new ResourceNotFoundException("No ser with role " + roleType + " has been found.")
        );

        return role.getUsers();
    }
}
