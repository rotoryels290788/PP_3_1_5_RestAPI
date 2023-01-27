package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;
public interface UserService {

    List<User> getList();
    void deleteUser(Long id);
    void editUser(User us);
    User getUserId(Long id);
    User getEmail(String email);
    boolean saveUser(User user);
}
