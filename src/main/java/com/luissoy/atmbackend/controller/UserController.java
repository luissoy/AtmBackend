package com.luissoy.atmbackend.controller;

import com.luissoy.atmbackend.exception.DataIntegrityException;
import com.luissoy.atmbackend.exception.StandardError;
import com.luissoy.atmbackend.model.User;
import com.luissoy.atmbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).
                    body(savedUser);
        } catch (DataIntegrityException e) {
            return ResponseEntity.
                    status(HttpStatus.BAD_REQUEST).
                    body(new StandardError(e.getMessage(), "POST /api/v1/users"));
        }
    }
}
