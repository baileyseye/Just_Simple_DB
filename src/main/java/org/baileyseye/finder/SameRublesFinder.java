package org.baileyseye.finder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.baileyseye.connection.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SameRublesFinder {
    private static final Logger logger = LogManager.getLogger(SameRublesFinder.class);

    public static Map<Integer, Integer> findSameRubles() {
        Map<Integer, Integer> sameRublesMap = new HashMap<>();
        String sql = "SELECT rubles, COUNT(*) AS count FROM magazine GROUP BY rubles HAVING COUNT(*) > 1";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int rubles = resultSet.getInt("rubles");
                int count = resultSet.getInt("count");
                sameRublesMap.put(rubles, count);
            }

            if (!sameRublesMap.isEmpty()) {
                logger.info("Найдены одинаковые значения rubles в базе данных:");
                sameRublesMap.forEach((rubles, count) ->
                        logger.info("Цена: {} рублей, Количество повторений: {}", rubles, count)
                );
            } else {
                logger.info("Одинаковые значения rubles в базе данных не найдены.");
            }
        } catch (SQLException e) {
            logger.error("Ошибка при выполнении запроса к базе данных.", e);
        }
        return sameRublesMap;
    }
}
