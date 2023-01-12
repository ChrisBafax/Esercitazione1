package it.java.course.esercitazione1.business;

import it.java.course.esercitazione1.model.Role;
import it.java.course.esercitazione1.model.RoleType;
import it.java.course.esercitazione1.model.User;

import java.util.ArrayList;
import java.util.List;

public interface RoleBO {

    ArrayList<Role> getAll();

    Role getByID(long id);

    Role create(Role role);

    String delete(long id);

    List<User> getRoleUsers(RoleType roleType);
}
