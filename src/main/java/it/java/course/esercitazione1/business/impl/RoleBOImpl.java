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
        ArrayList<Role> roles = (ArrayList<Role>) roleRepository.findAll();
        return roles;
    }

    public Role getByID(long id) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The role ID " + id +
                        " you are looking for does not exist in this database.")
        );
        return role;
    }

    public Role create(Role role) {
        Role roleN = roleRepository.save(role);
        return roleN;
    }

    public String delete(long id) {
        getByID(id);
        roleRepository.deleteById(id);
        return "The course with the ID " + id +
                " has been successfully been deleted";
    }

    public List<User> getRoleUsers(RoleType roleType) {
        Role role = roleRepository.findByRoleType(roleType).orElseThrow(
                () -> new ResourceNotFoundException("No ser with role " + roleType + " has been found.")
        );
        List<User> users = role.getUsers();
        return users;
    }
}
