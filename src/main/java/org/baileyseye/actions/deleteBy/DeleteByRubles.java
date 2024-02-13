package org.baileyseye.actions.deleteBy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.baileyseye.connection.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteByRubles {
    private static final Logger logger = LogManager.getLogger(DeleteByRubles.class);

    public static void delete(int rubles) {
        String sql = "DELETE FROM magazine WHERE rubles = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, rubles);
            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("{} записей удалено по цене в рублях '{}'.", rowsAffected, rubles);
        } catch (SQLException e) {
            logger.error("Ошибка при удалении записей из базы данных по цене в рублях.", e);
        }
    }
}
