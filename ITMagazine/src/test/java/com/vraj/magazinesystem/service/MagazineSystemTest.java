package com.vraj.magazinesystem.service;

import com.vraj.magazinesystem.model.Role;
import com.vraj.magazinesystem.model.Story;
import com.vraj.magazinesystem.model.User;
import com.vraj.magazinesystem.repository.StoryRepository;
import com.vraj.magazinesystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MagazineSystemTest {
    @Test
    void testSubmitStory() {
        UserRepository userRepo = mock(UserRepository.class);
        StoryRepository storyRepo = mock(StoryRepository.class);
        SecurityService securityService = mock(SecurityService.class);
        NotificationService notificationService = mock(NotificationService.class);
        PublishingService publishingService = mock(PublishingService.class);
        PaymentService paymentService = mock(PaymentService.class);

        MagazineSystem system = new MagazineSystem(
                securityService, storyRepo, userRepo,
                notificationService, publishingService, paymentService);

        when(securityService.hasRole(anyString(), eq(Role.JOURNALIST))).thenReturn(true);
        when(securityService.getUserId(anyString())).thenReturn(1L);

        Story story = new Story();
        story.setTitle("Test Story");
        story.setContent("This is a test story content.");
        story.setCategory("Tech");

        when(storyRepo.save(any(Story.class))).thenReturn(story);

        Story result = system.submitStory("validToken", "Test Story", "This is a test story content.", "Tech");

        assertNotNull(result, "Story should not be null");
        assertEquals("PendingReview", result.getStatus(), "Story status should be 'PendingReview'");
    }
}

