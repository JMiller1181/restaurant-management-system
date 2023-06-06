package org.restaurant.models;

import java.util.Map;

public class Sales {
    private double salesRevenue;
    private Map<MenuItem, Integer> popularItems;
    private Map<Table, Double> tablesWithMostOrders;

    public Sales(double salesRevenue, Map<MenuItem, Integer> popularItems, Map<Table, Double> tablesWithMostOrders) {
        this.salesRevenue = salesRevenue;
        this.popularItems = popularItems;
        this.tablesWithMostOrders = tablesWithMostOrders;
    }

    public double getSalesRevenue() {
        return salesRevenue;
    }

    public void setSalesRevenue(double salesRevenue) {
        this.salesRevenue = salesRevenue;
    }

    public Map<MenuItem, Integer> getPopularItems() {
        return popularItems;
    }

    public void setPopularItems(Map<MenuItem, Integer> popularItems) {
        this.popularItems = popularItems;
    }

    public Map<Table, Double> getTablesWithMostOrders() {
        return tablesWithMostOrders;
    }

    public void setTablesWithMostOrders(Map<Table, Double> tablesWithMostOrders) {
        this.tablesWithMostOrders = tablesWithMostOrders;
    }
}
