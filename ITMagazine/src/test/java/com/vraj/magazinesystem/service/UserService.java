package com.vraj.magazinesystem.service;

import com.vraj.magazinesystem.model.Role;
import com.vraj.magazinesystem.model.User;
import com.vraj.magazinesystem.repository.UserRepository;
import com.vraj.magazinesystem.util.PasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        User user = new User(null, "testUser", "test@example.com", "hashedPassword", Role.JOURNALIST);
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.registerUser("testUser", "test@example.com", "password", Role.JOURNALIST);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testAuthenticateUser() {
        String hashedPassword = PasswordHasher.hash("password");
        User user = new User(null, "testUser", "test@example.com", hashedPassword, Role.JOURNALIST);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        boolean result = userService.authenticateUser("test@example.com", "password");
        assertTrue(result);
    }
}