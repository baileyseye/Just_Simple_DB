package org.baileyseye.actions.updateBy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.baileyseye.connection.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RublesUpdater {
    private static final Logger logger = LogManager.getLogger(RublesUpdater.class);

    public static void updateRublesForName(String name, int rubles) {
        String sql = "UPDATE magazine SET rubles = ? WHERE name = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, rubles);
            preparedStatement.setString(2, name);
            int rowsAffected = preparedStatement.executeUpdate();

            logger.info("Обновлено {} записей для продукта '{}'.", rowsAffected, name);
        } catch (SQLException e) {
            logger.error("Ошибка при выполнении запроса к базе данных.", e);
        }
    }
}
