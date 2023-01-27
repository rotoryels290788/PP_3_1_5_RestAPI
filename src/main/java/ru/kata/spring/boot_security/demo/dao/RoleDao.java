package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;
import java.util.List;

public interface RoleDao {
    boolean saveRole(Role role);
    Role getRole(Long id);
    List<Role> getList();
    Role findByName(String name);
    List<Role> listByName(List<String> name);
}
