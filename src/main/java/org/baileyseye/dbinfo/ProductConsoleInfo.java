package org.baileyseye.dbinfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.baileyseye.connection.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductConsoleInfo {
    private static final Logger logger = LogManager.getLogger(ProductConsoleInfo.class);

    public static int showProductRublesCountByName(String productName) {
        String sql = "SELECT rubles FROM magazine WHERE name = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, productName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("rubles");
            } else {
                logger.info("Продукт с именем '{}' не найден.", productName);
                return -1;
            }
        } catch (SQLException e) {
            logger.error("Ошибка при выполнении запроса к базе данных.", e);
            return -1;
        }
    }

    public static List<String> showProductNamesByRubles(int rublesCount, boolean unique) {
        List<String> productNames = new ArrayList<>();
        String sql;
        if (unique) {
            sql = "SELECT DISTINCT name FROM magazine WHERE rubles = ?";
        } else {
            sql = "SELECT name FROM magazine WHERE rubles = ?";
        }

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, rublesCount);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String productName = resultSet.getString("name");
                productNames.add(productName);
            }
        } catch (SQLException e) {
            logger.error("Ошибка при выполнении запроса к базе данных.", e);
        }

        return productNames;
    }
}
