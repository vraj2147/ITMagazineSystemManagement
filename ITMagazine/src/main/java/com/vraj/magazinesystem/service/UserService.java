package com.vraj.magazinesystem.service;

import com.vraj.magazinesystem.model.Role;
import com.vraj.magazinesystem.model.User;
import com.vraj.magazinesystem.repository.UserRepository;
import com.vraj.magazinesystem.util.PasswordHasher;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user by hashing their password and saving them in the database.
     *
     * @param username The username of the new user.
     * @param email    The email of the new user.
     * @param password The plaintext password of the new user.
     * @param role     The role of the user (e.g., JOURNALIST, EDITOR).
     */
    public void registerUser(String username, String email, String password, Role role) {
        String hashedPassword = PasswordHasher.hash(password); // Hash the password
        User newUser = new User(null, username, email, hashedPassword, role);
        userRepository.save(newUser);
    }

    /**
     * Authenticates a user by verifying their password against the stored hash.
     *
     * @param email    The user's email.
     * @param password The plaintext password.
     * @return true if authentication is successful; false otherwise.
     */
    public boolean authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String storedHash = user.get().getHashedPassword();
            return PasswordHasher.matches(password, storedHash); // Verify password
        }
        return false;
    }
}
