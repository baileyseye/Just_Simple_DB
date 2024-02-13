package org.baileyseye.dbinfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.baileyseye.connection.DatabaseConnector;
import org.baileyseye.dao.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaxRublesProductGetter {
    private static final Logger logger = LogManager.getLogger(MaxRublesProductGetter.class);

    public static Product showMaxRublesProduct() {
        String sql = "SELECT * FROM magazine WHERE rubles = (SELECT MAX(rubles) FROM magazine)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int rubles = resultSet.getInt("rubles");
                return new Product(name, rubles);
            } else {
                logger.info("В магазине нет товаров.");
                return null;
            }
        } catch (SQLException e) {
            logger.error("Ошибка при выполнении запроса к базе данных.", e);
            return null;
        }
    }
}
