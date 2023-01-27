package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;


@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImp(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userSet = userService.getEmail(username);
        if (userSet == null) {
            throw new UsernameNotFoundException(String.format("Email is '%s' not found", username));
        }

        return new org.springframework.security.core.userdetails.User(userSet.getUsername(),
                userSet.getPassword(),
                userSet.getRoles());
    }

}