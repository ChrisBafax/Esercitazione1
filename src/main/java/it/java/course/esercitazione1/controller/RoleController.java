package it.java.course.esercitazione1.controller;

import it.java.course.esercitazione1.exception.ResourceNotFoundException;
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

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/role")
    // Look for all the saved roles
    public ResponseEntity<ArrayList<Role>> getRoles(){
        ArrayList<Role> _roles = (ArrayList<Role>) roleRepository.findAll();
        return new ResponseEntity<>(_roles, HttpStatus.OK);
    }

    @PostMapping("/role/Add")
    // Create a new Role
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        Role _role = roleRepository.save(role);
        return new ResponseEntity<>(_role,HttpStatus.OK);
    }
    @DeleteMapping("/role/{id}")
    // Delete an existing role
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable long id){
        roleRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/role/{roleType}/users")
    // Look for all user that have the given roleType
    public List<User> getUsersForCourse(@PathVariable("roleType") RoleType roleType) {
        Role role = roleRepository.findByRoleType(roleType).orElseThrow(
                () -> new ResourceNotFoundException("No ser with role " + roleType + " has been found.")
        );
        return role.getUsers();
    }
}
