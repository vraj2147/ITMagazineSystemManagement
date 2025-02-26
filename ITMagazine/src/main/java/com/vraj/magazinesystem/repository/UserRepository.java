package com.vraj.magazinesystem.repository;

import com.vraj.magazinesystem.config.DatabaseConfig;
import com.vraj.magazinesystem.model.Role;
import com.vraj.magazinesystem.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    /**
     * Saves or updates a user in the database.
     */
    public User save(User user) {
        String insertSql = "INSERT INTO users (username, email, hashed_password, role) VALUES (?, ?, ?, ?) RETURNING id";
        String updateSql = "UPDATE users SET username = ?, email = ?, hashed_password = ?, role = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection()) {
            if (user.getId() == null) {
                try (PreparedStatement stmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, user.getUsername());
                    stmt.setString(2, user.getEmail());
                    stmt.setString(3, user.getHashedPassword());
                    stmt.setString(4, user.getRole().name());
                    stmt.executeUpdate();
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            user.setId(rs.getLong(1));
                        }
                    }
                }
            } else {
                try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                    stmt.setString(1, user.getUsername());
                    stmt.setString(2, user.getEmail());
                    stmt.setString(3, user.getHashedPassword());
                    stmt.setString(4, user.getRole().name());
                    stmt.setLong(5, user.getId());
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error saving user: " + e.getMessage());
            throw new RuntimeException("Error saving user", e);
        }
        return user;
    }

    /**
     * Finds a user by their email address.
     */
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToUser(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding user by email: " + e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Helper method to map a ResultSet row to a User object.
     */
    private User mapRowToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setHashedPassword(rs.getString("hashed_password"));
        user.setRole(Role.valueOf(rs.getString("role")));
        return user;
    }
}
