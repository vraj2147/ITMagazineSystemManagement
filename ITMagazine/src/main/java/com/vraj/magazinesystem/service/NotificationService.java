package com.vraj.magazinesystem.service;

import com.vraj.magazinesystem.model.User;

/**
 * A simple service for sending notifications (email, Slack, etc.).
 */
public class NotificationService {

    public void notifyUser(User user, String message) {
        // Could integrate JavaMail, Slack, or SMS in a real system
        System.out.println("Notification to " + user.getEmail() + ": " + message);
    }
}
