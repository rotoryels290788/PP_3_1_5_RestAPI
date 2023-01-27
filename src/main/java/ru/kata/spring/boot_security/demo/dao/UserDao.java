package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    User getEmail(String email);
    void deleteUser(Long id);
    void editUser(User us);
    boolean saveUser(User user);
    List<User> getList();
    User getUserId(Long id);
}
