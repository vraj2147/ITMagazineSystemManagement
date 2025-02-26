package com.vraj.magazinesystem;

import com.vraj.magazinesystem.model.Invoice;
import com.vraj.magazinesystem.model.Role;
import com.vraj.magazinesystem.model.Story;
import com.vraj.magazinesystem.repository.StoryRepository;
import com.vraj.magazinesystem.repository.UserRepository;
import com.vraj.magazinesystem.service.*;
import com.vraj.magazinesystem.util.JwtTokenProvider;

public class Main {

    public static void main(String[] args) {
        // 1. Initialize repositories
        UserRepository userRepo = new UserRepository();
        StoryRepository storyRepo = new StoryRepository();

        // 2. Utility classes
        JwtTokenProvider jwtProvider = new JwtTokenProvider();
        SecurityService securityService = new SecurityService(userRepo, jwtProvider);

        // 3. Additional services
        NotificationService notificationService = new NotificationService();
        PublishingService publishingService = new PublishingService();
        PaymentService paymentService = new PaymentService();
        UserService userService = new UserService(userRepo);

        // 4. Orchestrator (MagazineSystem)
        MagazineSystem system = new MagazineSystem(
                securityService,
                storyRepo,
                userRepo,
                notificationService,
                publishingService,
                paymentService
        );

        // ------------------------------------------
        // DEMO: Register a Journalist and an Editor
        // ------------------------------------------
        String journalistEmail = "john@writers.com";
        String editorEmail = "alice@magazine.com";

        // Check and register users if they don't already exist
        try {
            if (!userRepo.findByEmail(journalistEmail).isPresent()) {
                userService.registerUser("John Writer", journalistEmail, "plainPassword", Role.JOURNALIST);
                System.out.println("Registered Journalist: " + journalistEmail);
            }

            if (!userRepo.findByEmail(editorEmail).isPresent()) {
                userService.registerUser("Alice Editor", editorEmail, "editorPassword", Role.EDITOR);
                System.out.println("Registered Editor: " + editorEmail);
            }
        } catch (Exception e) {
            System.out.println("Error registering users: " + e.getMessage());
            return;
        }

        // ------------------------------------------
        // DEMO: User workflow begins
        // ------------------------------------------

        // ---- Step A: Journalist logs in
        String journalistToken;
        try {
            journalistToken = securityService.login(journalistEmail, "plainPassword");
            System.out.println("Journalist logged in successfully.");
        } catch (SecurityException e) {
            System.out.println("Journalist login failed: " + e.getMessage());
            return;
        }

        // ---- Step B: Journalist submits a story
        Story submittedStory = system.submitStory(
                journalistToken,
                "My Life with Java",
                "This is a long content about Java experiences...",
                "Tech"
        );
        Long newStoryId = submittedStory.getId();
        System.out.println("Submitted story ID: " + newStoryId);

        // ---- Step C: Editor logs in
        String editorToken;
        try {
            editorToken = securityService.login(editorEmail, "editorPassword");
            System.out.println("Editor logged in successfully.");
        } catch (SecurityException e) {
            System.out.println("Editor login failed: " + e.getMessage());
            return;
        }

        // ---- Step D: Editor reviews the newly submitted story
        system.reviewStory(editorToken, newStoryId, "Approved", "Looks good, well done!");
        System.out.println("Editor reviewed and approved the story.");

        // ---- Step E: Publish the story & capture the invoice
        Invoice invoice = system.publishStory(editorToken, newStoryId);
        if (invoice != null) {
            // Payment was processed, invoice returned
            System.out.println("Story published. Invoice "
                    + invoice.getInvoiceNumber() + " status: " + invoice.getStatus());
        } else {
            // Probably the story wasn't "Approved"
            System.out.println("Story was not published, so no invoice generated.");
        }
    }
}
