package org.baileyseye.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.baileyseye.connection.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO {
    private static final Logger logger = LogManager.getLogger(ProductDAO.class);

    public static void addProduct(String name, int rubles) {
        String sql = "INSERT INTO magazine (name, rubles) VALUES (?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, rubles);
            preparedStatement.executeUpdate();
            logger.info("Record successfully added to the database.");
        } catch (SQLException e) {
            logger.error("Error adding record to the database.", e);
        }
    }

}
