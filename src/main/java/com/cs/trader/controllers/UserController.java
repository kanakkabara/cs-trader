package com.cs.trader.controllers;

import com.cs.trader.domain.User;
import com.cs.trader.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user")
    public int addNewUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }
}
