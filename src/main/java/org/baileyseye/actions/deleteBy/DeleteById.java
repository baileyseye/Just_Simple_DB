package org.baileyseye.actions.deleteBy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.baileyseye.connection.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteById {

    private static final Logger logger = LogManager.getLogger(DeleteById.class);

    public static void delete(int id) {
        String sql = "DELETE FROM magazine WHERE id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            logger.info("{} записей удалено по идентификатору '{}'.", rowsAffected, id);
        } catch (SQLException e) {
            logger.error("Ошибка при удалении записей из базы данных по идентификатору.", e);
        }
    }
}
