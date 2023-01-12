package it.java.course.esercitazione1.controller;

// Import from other packages
import it.java.course.esercitazione1.business.impl.RoleBOImpl;

import it.java.course.esercitazione1.model.Role;
import it.java.course.esercitazione1.model.RoleType;
import it.java.course.esercitazione1.model.User;

// Import from SpringFrameWork
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

// Import from Java
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    RoleBOImpl roleBO;

    @GetMapping("/role")
    // Look for all the saved roles
    public ResponseEntity<ArrayList<Role>> getRoles(){
        return new ResponseEntity<>(roleBO.getAll(), HttpStatus.OK);
    }

    @PostMapping("/role/Add")
    // Create a new Role
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        return new ResponseEntity<>(roleBO.create(role),HttpStatus.OK);
    }
    @DeleteMapping("/role/delete/{id}")
    // Delete an existing role
    public ResponseEntity<String> deleteRole(@PathVariable long id) {
        return new ResponseEntity<>(roleBO.delete(id),HttpStatus.NO_CONTENT);
    }

    @GetMapping("/role/{roleType}/users")
    // Look for all user that have the given roleType
    public ResponseEntity<List<User>> getUsersForRoleType(@PathVariable("roleType") RoleType roleType) {
        return new ResponseEntity<>(roleBO.getRoleUsers(roleType), HttpStatus.OK);
    }
}
