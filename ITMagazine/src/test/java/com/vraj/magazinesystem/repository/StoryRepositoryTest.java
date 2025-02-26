package com.vraj.magazinesystem.repository;

import com.vraj.magazinesystem.config.DatabaseConfig;
import com.vraj.magazinesystem.model.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StoryRepositoryTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private StoryRepository storyRepository;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        DatabaseConfig.setConnection(connection); // Inject mocked connection
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
        DatabaseConfig.resetConnection(); // Reset after each test
    }

    public Story save(Story story) throws SQLException {
        String insertSql = "INSERT INTO stories (title, content, category, status, author_id, publish_date) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
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

            stmt.executeUpdate();

            generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                story.setId(generatedKeys.getLong(1));
            }

            return story;
        } finally {
            // Proper resource cleanup
            if (generatedKeys != null) generatedKeys.close();
            if (stmt != null) stmt.close();
        }
    }

    @Test
    void testUpdateExistingStory() throws SQLException {
        // Arrange
        Story story = new Story(1L, "Updated Title", "Updated Content", "Tech", 1L);
        story.setStatus("Updated");
        String updateSql = "UPDATE stories SET title = ?, content = ?, category = ?, status = ?, publish_date = ? WHERE id = ?";

        when(connection.prepareStatement(eq(updateSql))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1); // Simulate successful update

        // Act
        Story updatedStory = storyRepository.save(story);

        // Assert
        assertNotNull(updatedStory, "Updated story should not be null");
        assertEquals("Updated", updatedStory.getStatus(), "Story status should match the updated value");

        // Verify interactions
        verify(preparedStatement).setString(1, story.getTitle());
        verify(preparedStatement).setString(2, story.getContent());
        verify(preparedStatement).setString(3, story.getCategory());
        verify(preparedStatement).setString(4, story.getStatus());
        verify(preparedStatement).setNull(5, Types.TIMESTAMP);
        verify(preparedStatement).setLong(6, story.getId());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testFindById() throws SQLException {
        // Arrange
        Long storyId = 1L;
        String selectSql = "SELECT * FROM stories WHERE id = ?";

        when(connection.prepareStatement(eq(selectSql))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(storyId);
        when(resultSet.getString("title")).thenReturn("Test Story");
        when(resultSet.getString("content")).thenReturn("Test Content");
        when(resultSet.getString("category")).thenReturn("Tech");
        when(resultSet.getString("status")).thenReturn("Published");
        when(resultSet.getLong("author_id")).thenReturn(1L);
        when(resultSet.getTimestamp("publish_date")).thenReturn(null);

        // Act
        Optional<Story> optionalStory = storyRepository.findById(storyId);

        // Assert
        assertTrue(optionalStory.isPresent(), "Story should be found");
        Story story = optionalStory.get();
        assertEquals(storyId, story.getId(), "Story ID should match the searched ID");
        assertEquals("Test Story", story.getTitle(), "Story title should match");
        assertEquals("Published", story.getStatus(), "Story status should match");

        // Verify interactions
        verify(preparedStatement).setLong(1, storyId);
        verify(preparedStatement).executeQuery();
    }

    @Test
    void testFindByIdNotFound() throws SQLException {
        // Arrange
        Long storyId = 2L;
        String selectSql = "SELECT * FROM stories WHERE id = ?";

        when(connection.prepareStatement(eq(selectSql))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Simulate no story found

        // Act
        Optional<Story> optionalStory = storyRepository.findById(storyId);

        // Assert
        assertFalse(optionalStory.isPresent(), "No story should be found for the given ID");

        // Verify interactions
        verify(preparedStatement).setLong(1, storyId);
        verify(preparedStatement).executeQuery();
    }
}
