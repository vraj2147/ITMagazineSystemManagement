package com.vraj.magazinesystem.util;



import com.vraj.magazinesystem.model.Role;
import com.vraj.magazinesystem.model.User;
import java.util.Base64;

/**
 * Demonstrates a minimal "fake JWT" using Base64-encoded strings.
 *
 * In real applications, use a library like "io.jsonwebtoken" (jjwt) or
 * "auth0/java-jwt" to properly sign and verify JWTs with HMAC or RSA.
 */
public class JwtTokenProvider {

    // In a real JWT approach, you'd sign tokens with a secret key using HMAC or RSA
    private String secret = "YourJwtSecretHere";

    /**
     * Creates a very simplified "token" by encoding user info in Base64.
     * This is NOT a secure production JWT approach, but it illustrates the concept.
     */
    public String createToken(User user) {
        // For example: "<userId>|<userRole>"
        String payload = user.getId() + "|" + user.getRole().name();
        // Encode with Base64
        return Base64.getEncoder().encodeToString(payload.getBytes());
    }

    /**
     * Extract the user's ID (Long) from the "token".
     */
    public Long getUserIdFromToken(String token) {
        String decoded = new String(Base64.getDecoder().decode(token));
        String[] parts = decoded.split("\\|");
        return Long.parseLong(parts[0]);
    }

    /**
     * Extract the user's Role from the "token".
     */
    public Role getRoleFromToken(String token) {
        String decoded = new String(Base64.getDecoder().decode(token));
        String[] parts = decoded.split("\\|");
        return Role.valueOf(parts[1]);
    }
}
