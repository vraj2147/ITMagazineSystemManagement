package com.vraj.magazinesystem.repository;

import com.vraj.magazinesystem.config.DatabaseConfig;
import com.vraj.magazinesystem.model.Story;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * A simple JDBC-based repository for the "stories" table.
 *
 * Example table schema:
 *   CREATE TABLE stories (
 *     id BIGSERIAL PRIMARY KEY,
 *     title VARCHAR(255),
 *     content TEXT,
 *     category VARCHAR(100),
 *     status VARCHAR(50),
 *     author_id BIGINT,         -- references users.id
 *     publish_date TIMESTAMP    -- optional for scheduled publication
 *   );
 */
public class StoryRepository {

    /**
     * Persists a Story (insert or update) in the DB.
     */
    public Story save(Story story) {
        try (Connection conn = DatabaseConfig.getConnection()) {
            if (story.getId() == null) {
                // INSERT (new story)
                String sql = "INSERT INTO stories (title, content, category, status, author_id, publish_date) "
                        + "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, story.getTitle());
                    stmt.setString(2, story.getContent());
                    stmt.setString(3, story.getCategory());
                    stmt.setString(4, story.getStatus());
                    stmt.setLong(5, story.getAuthorId());

                    if (story.getPublishDate() != null) {
                        stmt.setTimestamp(6, Timestamp.valueOf(story.getPublishDate()));
                    } else {
                        stmt.setNull(6, Types.TIMESTAMP);
                    }

                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        story.setId(rs.getLong("id"));
                    }
                }
            } else {
                // UPDATE (existing story)
                String sql = "UPDATE stories "
                        + "SET title = ?, content = ?, category = ?, status = ?, publish_date = ? "
                        + "WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, story.getTitle());
                    stmt.setString(2, story.getContent());
                    stmt.setString(3, story.getCategory());
                    stmt.setString(4, story.getStatus());
                    if (story.getPublishDate() != null) {
                        stmt.setTimestamp(5, Timestamp.valueOf(story.getPublishDate()));
                    } else {
                        stmt.setNull(5, Types.TIMESTAMP);
                    }
                    stmt.setLong(6, story.getId());
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return story;
    }

    /**
     * Find a story by primary key ID.
     */
    public Optional<Story> findById(Long id) {
        String sql = "SELECT * FROM stories WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Story story = mapRowToStory(rs);
                    return Optional.of(story);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Optionally add findAll(), findByStatus(), etc.

    /**
     * Helper method to map a ResultSet row to a Story object.
     */
    private Story mapRowToStory(ResultSet rs) throws SQLException {
        // Adjust constructor to match your Story class signature
        Story story = new Story(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getString("category"),
                rs.getLong("author_id")
        );
        story.setStatus(rs.getString("status"));

        Timestamp pubDate = rs.getTimestamp("publish_date");
        if (pubDate != null) {
            story.setPublishDate(pubDate.toLocalDateTime());
        }
        return story;
    }
}