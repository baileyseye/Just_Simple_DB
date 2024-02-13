package org.baileyseye.dao;

public class Product {
    private String name;
    private int rubles;

    public Product(String name, int rubles) {
        this.name = name;
        this.rubles = rubles;
    }

    public String getName() {
        return name;
    }

    public int getRubles() {
        return rubles;
    }
}
