package it.java.course.esercitazione1.business;

import it.java.course.esercitazione1.model.Role;
import it.java.course.esercitazione1.model.RoleType;
import it.java.course.esercitazione1.model.User;

import java.util.ArrayList;
import java.util.List;

public interface RoleBO {

    public ArrayList<Role> getAll();

    public Role getByID(long id);

    public Role create(Role role);

    public String delete(long id);

    public List<User> getRoleUsers(RoleType roleType);
}
