package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(7);
    }

    @Autowired
    public UserServiceImpl(UserDao userDao) {

        this.userDao = userDao;
    }
    @Override
    @Transactional
    public boolean saveUser(User user) {
        User userSet = userDao.getEmail(user.getUsername());
        if(userSet != null) {return false;}
        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        userDao.saveUser(user);
        return true;
    }
    @Override
    @Transactional
    public List<User> getList() {
        return userDao.getList();
    }
    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }
    @Override
    @Transactional
    public void editUser(User us) {
        User userBas = getUserId(us.getId());
        if(!userBas.getPassword().equals(us.getPassword())) {
            us.setPassword(bCryptPasswordEncoder().encode(us.getPassword()));
        }
        userDao.editUser(us);
    }
    @Override
    @Transactional
    public User getUserId(Long id) {
        return userDao.getUserId(id);
    }
    @Override
    @Transactional
    public User getEmail(String userName) {
        return userDao.getEmail(userName);
    }


}
