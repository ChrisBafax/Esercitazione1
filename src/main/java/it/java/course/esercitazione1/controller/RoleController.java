package it.java.course.esercitazione1.controller;

import it.java.course.esercitazione1.business.impl.RoleBOImpl;
import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.model.Role;
import it.java.course.esercitazione1.model.RoleType;
import it.java.course.esercitazione1.model.User;
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
    RoleBOImpl roleBO;

    @GetMapping("/role")
    // Look for all the saved roles
    public ResponseEntity<ArrayList<Role>> getRoles() {
        return new ResponseEntity<>(roleBO.getAll(), HttpStatus.OK);
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<Role> getRoleByID(@PathVariable long id) {
        Role role;
        try {
            role = roleBO.getByID(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("The role ID " + id +
                    " you are looking for does not exist in this database.");
        }
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping("/role/Add")
    // Create a new Role
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return new ResponseEntity<>(roleBO.create(role), HttpStatus.OK);
    }

    @DeleteMapping("/role/{id}/delete")
    // Delete an existing role
    public ResponseEntity<String> deleteRole(@PathVariable long id) {
        String strg;
        try {
            strg = roleBO.delete(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("The role ID " + id +
                    " you are trying to delete does not exist in this database.");
        }
        return new ResponseEntity<>(strg, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/role/{roleType}/users")
    // Look for all user that have the given roleType
    public ResponseEntity<List<User>> getUsersForRoleType(@PathVariable("roleType") RoleType roleType) {
        List<User> users;
        try {
            users = roleBO.getRoleUsers(roleType);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("No match with role " + roleType + " has been found.");
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
