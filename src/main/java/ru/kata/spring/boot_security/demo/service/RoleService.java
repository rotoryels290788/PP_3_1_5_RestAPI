package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    boolean saveRole(Role role);
    Role findByName(String name);
    List<Role> getList();
    Role getRoleId(Long id);
    List<Role> listByName(List<String> name);

}
