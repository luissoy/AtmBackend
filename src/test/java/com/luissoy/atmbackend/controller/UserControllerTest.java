package com.luissoy.atmbackend.controller;

import com.luissoy.atmbackend.exception.DataIntegrityException;
import com.luissoy.atmbackend.exception.StandardError;
import com.luissoy.atmbackend.model.User;
import com.luissoy.atmbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void getUsers() {
        // Given
        User user = new User();
        user.setUsername("user");
        List<User> users = List.of(user);

        // When
        when(userService.getUsers()).thenReturn(users);

        // Then
        List<User> retrievedUsers = userController.getUsers();
        assertEquals(1, retrievedUsers.size());
    }

    @Test
    public void saveUser_UsernameExists() {
        // Given
        User user = new User();
        user.setUsername("existingUsername");

        // When
        when(userService.saveUser(user)).thenThrow(new DataIntegrityException("Username already exists"));

        // Then
        ResponseEntity<?> responseEntity = userController.saveUser(user);
        assertEquals(StandardError.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void registerUser_UsernameDoesNotExist() {
        // Given
        User user = new User();
        user.setUsername("newUsername");

        // When
        when(userService.saveUser(user)).thenReturn(user);

        // Then
        ResponseEntity<?> responseEntity = userController.saveUser(user);
        assertEquals(User.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
}