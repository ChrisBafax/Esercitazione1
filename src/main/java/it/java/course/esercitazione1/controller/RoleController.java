package it.java.course.esercitazione1.controller;

import it.java.course.esercitazione1.model.Role;
import it.java.course.esercitazione1.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
}
