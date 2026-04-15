package com.example.learningplatform_backend.controller;

import com.example.learningplatform_backend.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    List<User> users = new ArrayList<>();

    @GetMapping
    public List<User> getUsers() {
        return users;
    }

    @PostMapping
    public String addUser(@RequestBody User user) {
        users.add(user);
        return "User added ✅";
    }
}