package com.vraj.magazinesystem.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database configuration class that supports setting a custom connection for testing.
 * It uses JDBC to connect to a database, reading connection details from application properties.
 */
public class DatabaseConfig {
    private static volatile Connection connection;  // Volatile to ensure thread-safe access

    /**
     * Retrieves the active database connection. If the connection is null or closed,
     * it initializes a new connection using application properties.
     *
     * @return an active database connection
     * @throws SQLException if unable to access the database
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            synchronized (DatabaseConfig.class) {
                if (connection == null || connection.isClosed()) {
                    initConnection();
                }
            }
        }
        return connection;
    }

    /**
     * Initializes the database connection using application properties.
     * This method is synchronized to avoid multiple initializations in a multi-threaded environment.
     */
    private static void initConnection() throws SQLException {
        Properties props = new Properties();
        try {
            // Load from the classpath resource: "/application.properties"
            props.load(DatabaseConfig.class.getResourceAsStream("/application.properties"));
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.username");
            String pass = props.getProperty("db.password");

            // Create the connection
            connection = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load database properties or create a connection", e);
        }
    }

    /**
     * Sets a custom connection (usually a mock) for testing purposes.
     *
     * @param mockConnection the mock connection to set
     */
    public static void setConnection(Connection mockConnection) {
        connection = mockConnection;
    }

    /**
     * Resets the connection to null, typically used after tests to clean up the custom connection.
     */
    public static void resetConnection() {
        connection = null;
    }
}
