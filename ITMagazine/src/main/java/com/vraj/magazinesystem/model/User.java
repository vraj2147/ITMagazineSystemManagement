package com.vraj.magazinesystem.model;

/**
 * Base class for all system users (Journalists, Editors, etc.).
 */
public class User {
    private Long id;
    private String username;
    private String email;
    private String hashedPassword;
    private Role role;  // JOURNALIST or EDITOR, etc.

    // Constructors
    public User() {
        // No-arg constructor for frameworks (if needed)
    }

    public User(Long id, String username, String email, String hashedPassword, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }
    public User(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.role = Role.JOURNALIST;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
