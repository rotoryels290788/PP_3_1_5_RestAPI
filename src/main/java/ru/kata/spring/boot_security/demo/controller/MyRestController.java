package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MyRestController {

    private final UserService userService;
    private final RoleService roleService;

    public MyRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public List<User> showUsers() {
        return userService.getList();
    }

    @PostMapping("/userNew")
    public User addNewUser(@RequestBody User user) {
        if (user.getRoles() != null) {
            List<String> list = user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toList());
            List<Role> roles = roleService.listByName(list);
            user.setRoles(roles);
        }
        userService.saveUser(user);
        return user;
    }

    @PatchMapping("/changeUser")
    public User changeUser(@RequestBody User user) {
        if (user.getRoles() != null) {
            List<String> list = user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toList());
            List<Role> roles = roleService.listByName(list);
            user.setRoles(roles);
        }
        userService.editUser(user);
        return user;
    }

    @GetMapping("/user/{id}")
    public User oneUserId(@PathVariable("id") Long id) {
        return userService.getUserId(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/listR")
    public List<Role> getRole() {
        return roleService.getList();
    }

    @GetMapping("/userPrincipal")
    public User getPrincipal(Principal principal) {
        return userService.getEmail(principal.getName());

    }


}
