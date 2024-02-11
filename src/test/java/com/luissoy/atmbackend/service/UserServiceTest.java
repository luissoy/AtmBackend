package com.luissoy.atmbackend.service;

import com.luissoy.atmbackend.exception.DataIntegrityException;
import com.luissoy.atmbackend.model.User;
import com.luissoy.atmbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void getUsers() {
        // Given
        User user = new User();
        user.setUsername("user");

        List<User> users = List.of(user);

        // When
        when(userRepository.findAll()).thenReturn(users);

        // Then
        List<User> retrievedUsers = userService.getUsers();
        assertEquals(1, retrievedUsers.size());
    }

    @Test
    public void saveUser_UsernameExists() {
        // Given
        User user = new User();
        user.setUsername("existingUsername");

        // When
        when(userRepository.existsUserByUsername("existingUsername")).thenReturn(true);

        // Then
        assertThrows(DataIntegrityException.class, () -> userService.saveUser(user));
    }

    @Test
    public void saveUser_UsernameDoesNotExist() {
        // Given
        User user = new User();
        user.setUsername("newUsername");

        // When
        when(userRepository.existsUserByUsername("newUsername")).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        // Then
        User savedUser = userService.saveUser(user);
        assertEquals(user, savedUser);
    }
}