package com.vraj.magazinesystem.model;

import java.time.LocalDateTime;

/**
 * Represents a submitted story/article with a title, content, status, category, etc.
 */
public class Story {

    private Long id;
    private String title;
    private String content;
    private String category;  // e.g., "Tech", "Lifestyle", "News"
    private String status;    // e.g. "Draft", "PendingReview", "Approved", "Rejected", "Published"
    private Long authorId;    // references the journalist's user ID
    private LocalDateTime publishDate; // for scheduling or record-keeping

    // Newly added fields:
    private String issueName;       // e.g., "June 2025 Issue"
    private String editorComments;  // notes or feedback from the editor

    public Story() {
        // No-arg constructor if needed by frameworks/tools
    }

    /**
     * Constructor to create a new Story with a default status of "Draft".
     *
     * @param id       Unique ID of the story (null if inserting a new row)
     * @param title    Title of the story/article
     * @param content  Main text content
     * @param category Category/genre of the story
     * @param authorId The user ID (journalist) who authored the story
     */
    public Story(Long id, String title, String content, String category, Long authorId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.authorId = authorId;
        this.status = "Draft";    // default
        this.publishDate = null;  // can be set later
        this.issueName = null;    // can be assigned upon publication
        this.editorComments = null; // set during editorial review
    }

    // --------- Getters & Setters ---------

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAuthorId() {
        return authorId;
    }
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public String getIssueName() {
        return issueName;
    }
    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public String getEditorComments() {
        return editorComments;
    }
    public void setEditorComments(String editorComments) {
        this.editorComments = editorComments;
    }
}
