package com.vraj.magazinesystem.repository;

import com.vraj.magazinesystem.config.DatabaseConfig;
import com.vraj.magazinesystem.model.Role;
import com.vraj.magazinesystem.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private UserRepository userRepository;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() throws SQLException {
        closeable = MockitoAnnotations.openMocks(this);
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Assume only one record is returned
        when(resultSet.getLong(1)).thenReturn(1L);

        DatabaseConfig.setConnection(connection);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
        DatabaseConfig.resetConnection();
    }

    @Test
    void testSaveNewUser() throws SQLException {
        User user = new User(null, "newuser", "newuser@example.com", "hashedPassword", Role.JOURNALIST);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId(), "User should have an ID after being saved");
        assertEquals(1L, savedUser.getId(), "User ID should be set by the database");
    }

    @Test
    void testUpdateUser() throws SQLException {
        User user = new User(1L, "updateduser", "updateduser@example.com", "updatedHashedPassword", Role.EDITOR);
        User updatedUser = userRepository.save(user);

        assertEquals("updateduser", updatedUser.getUsername(), "User username should be updated");
    }
}
