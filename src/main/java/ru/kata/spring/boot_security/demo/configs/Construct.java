package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Construct {
    private final UserService userService;
    private final RoleService rolesService;

    @Autowired
    public Construct(UserService userService, RoleService rolesService) {

        this.userService = userService;
        this.rolesService = rolesService;
    }


    @PostConstruct
    public void createTable() {

        Role admin = new Role("ROLE_ADMIN");
        Role rolUser = new Role("ROLE_USER");
        rolesService.saveRole(admin);
        rolesService.saveRole(rolUser);

        List<Role> role = new ArrayList<>();
        role.add(admin);
        List<Role> role2 = new ArrayList<>();
        role2.add(rolUser);
        List<Role> role3 = new ArrayList<>();
        role3.add(admin);
        role3.add(rolUser);


        User user = new User("Dmitry","1", 34, "IT",
                "dima@.ru", role3);

        User user2 = new User("Ivan","2", 23, "IT",
                "Iva@.ru", role2);

        User user3 = new User("Aleksey","3", 43, "IT",
                "Aleks@.ru", role);


        userService.saveUser(user);
        userService.saveUser(user2);
        userService.saveUser(user3);

    }
}
