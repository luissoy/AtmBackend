package com.luissoy.atmbackend.service;

import com.luissoy.atmbackend.exception.DataIntegrityException;
import com.luissoy.atmbackend.repository.UserRepository;
import com.luissoy.atmbackend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        if (userRepository.existsUserByUsername(user.getUsername())) {
            throw new DataIntegrityException("Username already exists");
        }

        return userRepository.save(user);
    }
}
