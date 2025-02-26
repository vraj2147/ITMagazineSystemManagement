package com.vraj.magazinesystem.model;

/**
 * Specialized subclass of User representing an Editor.
 *
 * Has editorial capabilities like approving or rejecting a story.
 */
public class Editor extends User {

    public Editor(Long id, String username, String email, String hashedPassword) {
        // Assign an EDITOR role
        super(id, username, email, hashedPassword, Role.EDITOR);
    }

    // Editor can mark the story as Approved
    public void approveStory(Story story) {
        story.setStatus("Approved");
    }

    // Editor can mark the story as Rejected
    public void rejectStory(Story story) {
        story.setStatus("Rejected");
    }
}
