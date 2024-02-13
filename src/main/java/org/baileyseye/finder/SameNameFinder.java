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

public class SameNameFinder {
    private static final Logger logger = LogManager.getLogger(SameNameFinder.class);

    public static Map<String, Integer> findSameNames() {
        Map<String, Integer> sameNamesMap = new HashMap<>();
        String sql = "SELECT name, COUNT(*) AS count FROM magazine GROUP BY name HAVING COUNT(*) > 1";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int count = resultSet.getInt("count");
                sameNamesMap.put(name, count);
            }

            if (!sameNamesMap.isEmpty()) {
                logger.info("Найдены одинаковые значения name в базе данных:");
                sameNamesMap.forEach((name, count) ->
                        logger.info("Имя: {}, Количество повторений: {}", name, count)
                );
            } else {
                logger.info("Одинаковые значения name в базе данных не найдены.");
            }
        } catch (SQLException e) {
            logger.error("Ошибка при выполнении запроса к базе данных.", e);
        }
        return sameNamesMap;
    }
}
