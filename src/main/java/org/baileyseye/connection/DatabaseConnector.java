package org.baileyseye.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final Logger logger = LogManager.getLogger(DatabaseConnector.class);

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            logger.info("Connected to the database.");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Failed to connect to the database.", e);
            throw new SQLException("Failed to connect to the database.");
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Connection to the database closed.");
            } catch (SQLException e) {
                logger.error("Failed to close the database connection.", e);
            }
        }
    }
}