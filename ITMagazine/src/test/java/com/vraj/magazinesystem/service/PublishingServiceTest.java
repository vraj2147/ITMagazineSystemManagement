package com.vraj.magazinesystem.service;



import com.vraj.magazinesystem.model.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class PublishingServiceTest {

    @InjectMocks
    private PublishingService publishingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPublishStory() {
        Story story = new Story();
        story.setStatus("Approved");

        publishingService.publishStory(story, "Issue1");

        assertEquals("Published", story.getStatus());
        assertNotNull(story.getPublishDate());
    }

    @Test
    void testScheduleStory() {
        Story story = new Story();
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);

        publishingService.scheduleStory(story, futureDate, "Future Issue");

        assertFalse(publishingService.getPublishedStories().contains(story));
        assertTrue(publishingService.getScheduledStories().contains(story));
    }
}
