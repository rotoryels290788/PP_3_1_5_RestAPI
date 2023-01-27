package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public User getEmail(String email) {
                return entityManager.createQuery("select u from User u join fetch u.roles where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList().stream().findAny().orElse(null);
    }
    @Override
    public  void deleteUser(Long id) {
        User us = entityManager.find(User.class, id);
        entityManager.remove(us);
    }
    @Override
    public void editUser(User us) {
        entityManager.merge(us);
    }

    @Override
    public boolean saveUser(User user) {
        entityManager.persist(user);
        return true;
    }
    @Override
    public List<User> getList() {
        return entityManager.createQuery("select s from User s", User.class).getResultList();
    }
    @Override
    public User getUserId(Long id) {
        return entityManager.find(User.class, id);
    }
}

