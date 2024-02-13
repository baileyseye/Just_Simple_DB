package org.baileyseye;

import org.baileyseye.actions.deleteBy.DeleteById;
import org.baileyseye.actions.deleteBy.DeleteByName;
import org.baileyseye.actions.deleteBy.DeleteByRubles;
import org.baileyseye.actions.updateBy.RublesUpdater;
import org.baileyseye.connection.DatabaseConnector;
import org.baileyseye.dao.Product;
import org.baileyseye.dao.ProductDAO;
import org.baileyseye.dbinfo.MaxRublesProduct;
import org.baileyseye.dbinfo.MaxRublesProductGetter;
import org.baileyseye.dbinfo.ProductConsoleInfo;
import org.baileyseye.dbinfo.TotalRublesCalculator;
import org.baileyseye.finder.SameNameFinder;
import org.baileyseye.finder.SameRublesFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        connectToDB();
        showProductRublesByName("Метаморфоза");
        findSameNames();
        showMaxRublesProductInfo();
        showProductNameByRubles(1500, true);
    }


    private static void connectToDB() {
        try (Connection connection = DatabaseConnector.getConnection()) {
        } catch (SQLException e) {
            logger.error("Failed to connect to the database", e);
        }
    }

    private static void addElement() {
        ProductDAO.addProduct("Пинокио", 175);
        ProductDAO.addProduct("Метаморфоза", 100000);
    }

    private static void deleteByID(int id){
        DeleteById.delete(id);
    }

    private static void deleteByName(String name){
        DeleteByName.delete(name);
    }

    private static void deleteByRubles(int rubles){
        DeleteByRubles.delete(rubles);
    }

    private static void showMaxRublesProduct(){
        MaxRublesProduct.showMaxRublesProduct();
    }

    public static void showMaxRublesProductInfo() {
        Product maxRublesProduct = MaxRublesProductGetter.showMaxRublesProduct();
        if (maxRublesProduct != null) {
            logger.info("Самый дорогой продукт в магазине:");
            logger.info("Название: {}", maxRublesProduct.getName());
            logger.info("Цена: {} рублей", maxRublesProduct.getRubles());
        } else {
            logger.info("Информация о продукте недоступна.");
        }
    }

    private static int showProductRublesByName(String name) {
        int rublesCount = ProductConsoleInfo.showProductRublesCountByName(name);
        if (rublesCount != -1) {
            logger.info("Цена продукта '{}': {} рублей", name, rublesCount);
        }
        return rublesCount;
    }

    private static void showProductNameByRubles(int rubles, boolean unique) {
        List<String> productNames = ProductConsoleInfo.showProductNamesByRubles(rubles, unique);
        if (!productNames.isEmpty()) {
            logger.info("Продукты с ценой {} рублей:", rubles);
            productNames.forEach(logger::info);
        } else {
            logger.info("Продукты с ценой {} рублей не найдены.", rubles);
        }
    }

    private static void findSameNames() {
        Map<String, Integer> sameNamesMap = SameNameFinder.findSameNames();
        if (!sameNamesMap.isEmpty()) {
            logger.info("Одинаковые значения name в базе данных:");
            sameNamesMap.forEach(
                    (name, count) -> logger.info("Имя: {}, Количество повторений: {}", name, count)
            );
        } else {
            logger.info("Одинаковые значения name в базе данных не найдены.");
        }
    }


    private static void findSameRubles() {
        Map<Integer, Integer> sameRublesMap = SameRublesFinder.findSameRubles();
        if (!sameRublesMap.isEmpty()) {
            logger.info("Одинаковые значения rubles в базе данных:");
            sameRublesMap.forEach(
                    (rubles, count) -> logger.info
                            ("Цена: {} рублей, Количество повторений: {}", rubles, count)
            );
        } else {
            logger.info("Одинаковые значения rubles в базе данных не найдены.");
        }
    }

    private static void calculateAndShowTotalRubles() {
        int totalRubles = TotalRublesCalculator.calculateTotalRubles();
        logger.info("Общее значение rubles в базе данных: {}", totalRubles);
    }

    private static void updateRublesForName(String name, int rubles) {
        RublesUpdater.updateRublesForName(name, rubles);
    }


}