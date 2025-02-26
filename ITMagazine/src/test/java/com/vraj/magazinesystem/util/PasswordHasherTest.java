package com.vraj.magazinesystem.util;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

class PasswordHasherTest {

    @Test
    void hashPassword_andVerifyMatch() {
        String password = "securePassword";
        String hashedPassword = PasswordHasher.hash(password);

        Assertions.assertTrue(PasswordHasher.matches(password, hashedPassword),
                "Hashed password should match the original.");
    }

    @Test
    void verifyMismatch_whenWrongPassword() {
        String password = "securePassword";
        String wrongPassword = "wrongPassword";
        String hashedPassword = PasswordHasher.hash(password);

        Assertions.assertFalse(PasswordHasher.matches(wrongPassword, hashedPassword),
                "Hashed password should not match the incorrect password.");
    }
}
