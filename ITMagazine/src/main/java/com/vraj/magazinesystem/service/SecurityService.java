package com.vraj.magazinesystem.service;

import com.vraj.magazinesystem.model.User;
import com.vraj.magazinesystem.model.Role;
import com.vraj.magazinesystem.repository.UserRepository;
import com.vraj.magazinesystem.util.JwtTokenProvider;
import com.vraj.magazinesystem.util.PasswordHasher;

import java.util.Optional;

/**
 * Handles user login and JWT token logic.
 */
public class SecurityService {

    private UserRepository userRepo;
    private JwtTokenProvider jwtProvider;

    public SecurityService(UserRepository userRepo, JwtTokenProvider jwtProvider) {
        this.userRepo = userRepo;
        this.jwtProvider = jwtProvider;
    }

    /**
     * Attempt to log in a user by email + raw password.
     * If valid, returns a JWT token; else throws SecurityException.
     */
    public String login(String email, String rawPassword) {
        Optional<User> opt = userRepo.findByEmail(email);
        if (opt.isPresent()) {
            User user = opt.get();
            // Check hashed password
            if (PasswordHasher.matches(rawPassword, user.getHashedPassword())) {
                // Generate a token
                return jwtProvider.createToken(user);
            }
        }
        throw new SecurityException("Invalid credentials.");
    }

    /**
     * Checks if a JWT token belongs to a user with the required role.
     */
    public boolean hasRole(String token, Role required) {
        Role userRole = jwtProvider.getRoleFromToken(token);
        return userRole == required;
    }

    /**
     * Extracts the user's ID from a given JWT token.
     */
    public Long getUserId(String token) {
        return jwtProvider.getUserIdFromToken(token);
    }
}