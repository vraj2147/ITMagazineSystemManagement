package com.vraj.magazinesystem.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Utility class for hashing and verifying passwords using BCrypt.
 */
public class PasswordHasher {

    /**
     * Hashes a plaintext password using BCrypt.
     *
     * @param password The plaintext password to hash.
     * @return A BCrypt-hashed version of the password.
     */
    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Verifies if a plaintext password matches a stored BCrypt hash.
     *
     * @param rawPassword   The plaintext password.
     * @param hashedPassword The stored BCrypt hash.
     * @return true if the passwords match; false otherwise.
     */
    public static boolean matches(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
