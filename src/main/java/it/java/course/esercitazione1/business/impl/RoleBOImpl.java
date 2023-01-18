package it.java.course.esercitazione1.business.impl;

import it.java.course.esercitazione1.business.RoleBO;
import it.java.course.esercitazione1.exception.ResourceNotFoundException;
import it.java.course.esercitazione1.model.Role;
import it.java.course.esercitazione1.model.RoleType;
import it.java.course.esercitazione1.model.User;
import it.java.course.esercitazione1.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleBOImpl implements RoleBO {

    @Autowired
    RoleRepository roleRepository;

    public ArrayList<Role> getAll() {
        return (ArrayList<Role>) roleRepository.findAll();
    }

    public Role getByID(long id) throws ResourceNotFoundException {
        return roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Role ID.")
        );
    }

    public Role create(Role role) {
        return roleRepository.save(role);
    }

    public String delete(long id) throws ResourceNotFoundException {
        getByID(id);
        roleRepository.deleteById(id);
        return "The course with the ID " + id +
                " has been successfully been deleted";
    }

    public List<User> getRoleUsers(RoleType roleType) throws ResourceNotFoundException {
        Role role = roleRepository.findByRoleType(roleType).orElseThrow(
                () -> new ResourceNotFoundException("No role.")
        );
        return role.getUsers();
    }
}
