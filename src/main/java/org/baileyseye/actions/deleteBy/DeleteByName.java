package org.baileyseye.actions.deleteBy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.baileyseye.connection.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteByName {
    private static final Logger logger = LogManager.getLogger(DeleteByName.class);

    public static void delete(String name) {
        String sql = "DELETE FROM magazine WHERE name = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("{} записей удалено по имени '{}'.", rowsAffected, name);
        } catch (SQLException e) {
            logger.error("Ошибка при удалении записей из базы данных по имени.", e);
        }
    }
}
