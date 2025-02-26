package com.vraj.magazinesystem.service;

import com.vraj.magazinesystem.model.*;
import com.vraj.magazinesystem.repository.StoryRepository;
import com.vraj.magazinesystem.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Central orchestrator for the "Submit Story" use case and related flows.
 */


public class MagazineSystem {

    private SecurityService securityService;
    private StoryRepository storyRepo;
    private UserRepository userRepo;
    private NotificationService notificationService;
    private PublishingService publishingService;
    private PaymentService paymentService;

    public MagazineSystem(SecurityService securityService,
                          StoryRepository storyRepo,
                          UserRepository userRepo,
                          NotificationService notificationService,
                          PublishingService publishingService,
                          PaymentService paymentService) {
        this.securityService = securityService;
        this.storyRepo = storyRepo;
        this.userRepo = userRepo;
        this.notificationService = notificationService;
        this.publishingService = publishingService;
        this.paymentService = paymentService;
    }

    // ---- STEP 1: Submit a story by a journalist
    public Story submitStory(String token, String title, String content, String category) {
        // 1. Validate role
        if (!securityService.hasRole(token, Role.JOURNALIST)) {
            throw new SecurityException("Only journalists can submit stories.");
        }

        // 2. Determine user ID from token
        Long userId = securityService.getUserId(token);

        // 3. Create & save the story
        Story story = new Story(null, title, content, category, userId);
        story.setStatus("PendingReview");
        storyRepo.save(story);

        // 4. Notify an editor
        Optional<User> maybeEditor = findSingleEditor();
        maybeEditor.ifPresent(ed -> notificationService.notifyUser(
                ed, "New story submitted: " + story.getTitle()
        ));

        return story;
    }

    // ---- STEP 2: Editor reviews & updates story status
    public Story reviewStory(String token, Long storyId, String newStatus, String comments) {
        // 1. Editor role check
        if (!securityService.hasRole(token, Role.EDITOR)) {
            throw new SecurityException("Only editors can review stories.");
        }

        // 2. Fetch story from DB
        Story story = storyRepo.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("No story found with id: " + storyId));

        // 3. Possibly add editor comments and set new status
        story.setEditorComments(comments);
        story.setStatus(newStatus);
        storyRepo.save(story);

        return story;
    }

    // ---- STEP 3: Publish & Pay
    // NOW returns an Invoice so the caller can see final payment details
    public Invoice publishStory(String token, Long storyId) {
        // 1. Editor role check
        if (!securityService.hasRole(token, Role.EDITOR)) {
            throw new SecurityException("Only editors can publish stories.");
        }

        // 2. Fetch story
        Story story = storyRepo.findById(storyId)
                .orElseThrow(() -> new IllegalArgumentException("Story not found with ID: " + storyId));

        // 3. Must be approved before publishing
        if ("Approved".equalsIgnoreCase(story.getStatus())) {
            // a) Publish it
            publishingService.publishStory(story, "Current Issue");
            story.setStatus("Published");
            story.setPublishDate(LocalDateTime.now());
            storyRepo.save(story);

            // b) Payment
            Invoice invoice = paymentService.generateInvoice(story.getAuthorId(), 100.0);
            paymentService.processPayment(invoice);

            return invoice; // Return the final invoice
        } else {
            System.out.println("Cannot publish unless story status is 'Approved'.");
            return null; // Return null if not published
        }
    }

    // Simple helper to find the first Editor in the system
    private Optional<User> findSingleEditor() {
        // In a real system, you'd query userRepo for a user with Role.EDITOR
        return userRepo.findByEmail("alice@magazine.com");
    }
}