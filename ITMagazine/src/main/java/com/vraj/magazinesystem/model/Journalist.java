package com.vraj.magazinesystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Specialized subclass of User representing Journalists.
 *
 * Can store a list of stories they've submitted, or unique methods
 * like submitStory(...) if you want that logic in the domain object.
 */
public class Journalist extends User {

    // Keep track of stories submitted by this Journalist
    private List<Story> submittedStories = new ArrayList<>();

    public Journalist(Long id, String username, String email, String hashedPassword) {
        // Assign a JOURNALIST role
        super(id, username, email, hashedPassword, Role.JOURNALIST);
    }

    public List<Story> getSubmittedStories() {
        return submittedStories;
    }

    public void addStory(Story story) {
        this.submittedStories.add(story);
    }
}