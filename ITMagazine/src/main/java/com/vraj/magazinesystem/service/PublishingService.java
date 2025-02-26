package com.vraj.magazinesystem.service;

import com.vraj.magazinesystem.model.Story;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Demonstrates an expanded PublishingService with:
 *  1) Scheduling stories to be published at a future date/time.
 *  2) Marking a published issue (i.e., storing stories under a named "magazine issue").
 */
public class PublishingService {

    // Keep a list of stories that are scheduled for future publication.
    // In production, you'd likely store this in a DB or queue.
    private List<Story> scheduledStories = new ArrayList<>();

    // Keep a list of stories that are already published (optionally track by issue).
    private List<Story> publishedStories = new ArrayList<>();

    /**
     * Immediately publishes the given story: sets status to "Published"
     * and optionally assigns it to a named issue.
     */
    public void publishStory(Story story, String issueName) {
        // Possibly do final checks
        System.out.println("Publishing story: " + story.getTitle() + " in issue: " + issueName);

        // Mark the story as published
        story.setStatus("Published");
        story.setPublishDate(LocalDateTime.now());
        story.setIssueName(issueName);  // You must add an 'issueName' field in your Story class

        // Add to published list
        publishedStories.add(story);
    }

    /**
     * Schedules the story to be automatically published on publishDate,
     * rather than publishing right away.
     */
    public void scheduleStory(Story story, LocalDateTime publishDate, String issueName) {
        // Set a future publish date
        story.setPublishDate(publishDate);
        story.setIssueName(issueName);
        // scheduledStories is an in-memory list in this simple example
        scheduledStories.add(story);

        System.out.println("Story '" + story.getTitle()
                + "' scheduled for publication on " + publishDate
                + " under issue: " + issueName);
    }

    /**
     * Periodic check to see if any scheduled stories are ready to be published now (or in the past).
     * In a real system, you'd call this method on a timer or by a scheduled job.
     */
    public void checkScheduledStories() {
        LocalDateTime now = LocalDateTime.now();
        Iterator<Story> iterator = scheduledStories.iterator();

        while (iterator.hasNext()) {
            Story story = iterator.next();
            // If it's time to publish
            if (story.getPublishDate() != null && !now.isBefore(story.getPublishDate())) {
                // We can now publish it
                System.out.println("Auto-publishing scheduled story: " + story.getTitle());
                story.setStatus("Published");
                publishedStories.add(story);
                iterator.remove(); // remove from the scheduling list
            }
        }
    }

    /**
     * Returns the list of published stories (you might group them by issue if you want).
     */
    public List<Story> getPublishedStories() {
        return publishedStories;
    }

    /**
     * Returns the list of stories currently scheduled for future publication.
     */
    public List<Story> getScheduledStories() {
        return scheduledStories;
    }
}


