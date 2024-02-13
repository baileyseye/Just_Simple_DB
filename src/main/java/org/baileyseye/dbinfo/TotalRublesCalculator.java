package org.baileyseye.dbinfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.baileyseye.connection.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TotalRublesCalculator {
    private static final Logger logger = LogManager.getLogger(TotalRublesCalculator.class);

    public static int calculateTotalRubles() {
        int totalRubles = 0;
        String sql = "SELECT SUM(rubles) AS total FROM magazine";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalRubles = resultSet.getInt("total");
                logger.info("Общая стоимость всех продуктов в базе данных: {} рублей", totalRubles);
            }
        } catch (SQLException e) {
            logger.error("Ошибка при выполнении запроса к базе данных.", e);
        }

        return totalRubles;
    }
}
