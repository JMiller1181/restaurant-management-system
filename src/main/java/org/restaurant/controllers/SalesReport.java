package org.restaurant.controllers;

import org.restaurant.models.MenuItem;
import org.restaurant.models.Order;
import org.restaurant.models.Table;
import org.restaurant.models.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SalesReport {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  //formatter for file name and exact date and time
    private static final DecimalFormat decimalFormatter = new DecimalFormat("0.00");    //formatting money
    private Map<MenuItem, Integer> menuItem;
    private Map<Table, Double> tables;
    private List<Order> orderList;
    private User user;

    public SalesReport() {
    }

    public SalesReport(Map<MenuItem, Integer> menuItem, Map<Table, Double> tables, User user) {
        this.menuItem = menuItem;
        this.tables = tables;
        this.user = user;
    }

    private double getTotalSalesRevenue(List<Order> orderList) {
        double totalRevenue = 0;
        for(Order order: orderList){
            totalRevenue += order.getTotalPrice();
        }
        return totalRevenue;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Map<String, Integer> getPopularItems(List<Order> orderList) {
        Map<String, Integer> popularItems = new HashMap<>();
        LinkedHashMap<String, Integer> sortedPopularItems = new LinkedHashMap<>();
        ArrayList<Integer> list = new ArrayList<>();
        for(Order order: orderList){
            for (MenuItem item: order.getItemsOrderedList()){
                String name = item.getName();
                if(!popularItems.containsKey(name)){
                    popularItems.put(name, 1);
                } else {
                    popularItems.put(name, popularItems.get(name) + 1);
                }
            }
        }
        for (Map.Entry<String, Integer> entry : popularItems.entrySet()) {
            list.add(entry.getValue());
        }
        list.sort(new Comparator<Integer>() {
            public int compare(Integer integer1, Integer integer2) {
                return (integer1).compareTo(integer2);
            }
        });
        Collections.reverse(list);
        for (Integer integer : list) {
            for (Map.Entry<String, Integer> entry : popularItems.entrySet()) {
                if (entry.getValue().equals(integer)) {
                    sortedPopularItems.put(entry.getKey(), integer);
                }
            }
        }
        return sortedPopularItems;
    }
    public Map<Integer, Double> getTableRevenue(List<Order> orderList) {
        Map<Integer, Double> tableRevenue = new HashMap<>();
        List<Double> list = new ArrayList<>();
        LinkedHashMap<Integer, Double> sortedTables = new LinkedHashMap<>();
        for(Order order: orderList){
            int tableID = order.getOrderTableID();
            double bill = order.getTotalPrice();
            if(!tableRevenue.containsKey(tableID)){
                tableRevenue.put(tableID, bill);
            } else {
                tableRevenue.put(tableID, tableRevenue.get(tableID) + bill);
            }
        }
        for(Map.Entry<Integer, Double> entry: tableRevenue.entrySet()){
            list.add(entry.getValue());
        }
        list.sort(new Comparator<Double>() {
            public int compare(Double double1, Double double2) {
                return (double1).compareTo(double2);
            }
        });
        Collections.reverse(list);
        for(Double dbl: list){
            for (Map.Entry<Integer, Double> entry: tableRevenue.entrySet()){
                if (entry.getValue().equals(dbl)){
                    sortedTables.put(entry.getKey(), dbl);
                }
            }
        }
        return sortedTables;
    }

    public String tableRevenueToString(Map<Integer, Double> map){
        String tableRevenue = "";
        Set<Integer> keyNames = map.keySet();
        for (int i: keyNames){
            tableRevenue += "Table " + i + ": $" + map.get(i) + "\n";
        }
        return tableRevenue;
    }

    public String popularItemsToString(Map<String, Integer> map){
        String popularItems = "";
        Set<String> keyNames = map.keySet();
        for (String name: keyNames){
            popularItems += name + ": " + map.get(name) + "\n";
        }
        return popularItems;
    }

    public String printOrders(List<Order> orderList){
        String printedOrders = "";
        for (Order order: orderList){
            printedOrders += order + "\n";
        }
        return printedOrders;
    }
    public void generateDailySalesReport() {

    }

    private void exportDailySalesReport() {
        LocalDateTime current = LocalDateTime.now();    //getting exact time stamp
        String fileName = dateTimeFormatter.format(current) + ".txt";   //setting it to make the name of files different and can print at any second with changes
        String filePath = "src/main/java/org/restaurant/utils/reports/" + fileName; //file path

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {    //creating buffer writer to export to new file

            //checking path
            File file = new File(filePath);
            if (file.exists()) {
                System.out.println("Sales Report file created successfully!");
            } else {
                System.out.println("Failed to create Sales Report file.\n");
            }

            //writing what should export to the report
            writer.write("-----------------------------\n Daily Sales Report \n " +
                    dateTimeFormatter.format(current) + "\n-----------------------------\n" +
                    "Total Revenue: $" + getTotalSalesRevenue(orderList) + "\n\n" +
                    "Most Popular Items: \n" +
                    popularItemsToString(getPopularItems(orderList)) + "\n" +
                    "Tables Sales: \n" +
                    tableRevenueToString(getTableRevenue(orderList)) + "\n" +
                    "Detailed Orders: \n" +
                    printOrders(orderList));

            writer.flush();     //flush to access right away - without having to rerun, jut when you exit from menu

            System.out.println("Sales Report printed to a new file successfully!");

        } catch (IOException e) {
            System.out.println("Unable to print report. " + e.getMessage());
        }
    }

    public void printSalesReport() {
        LocalDateTime current = LocalDateTime.now();

        //printing to console - same as export but with *color*
        System.out.println("-----------------------------\n Daily Sales Report \n " +
                dateTimeFormatter.format(current) + "\n-----------------------------\n" +
                "Total Revenue: $" + getTotalSalesRevenue(orderList) + "\n\n" +
                "Most Popular Items: \n" +
                popularItemsToString(getPopularItems(orderList)) + "\n" +
                "Tables Sales: \n" +
                tableRevenueToString(getTableRevenue(orderList)) + "\n" +
                "Detailed Orders: \n" +
                printOrders(orderList));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SalesReport report = new SalesReport();

        boolean exit = false;
        while (!exit) {
            System.out.println("""
                    MANAGER, would you like to:\s
                    1. Export report to a new file
                    2. Print report to console
                    3. Exit""");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> report.exportDailySalesReport();
                case 2 -> report.printSalesReport();
                case 3 -> {
                    System.out.println("Good-bye!");
                    exit = true;
                }
                default -> System.out.println("Invalid entry, try again");
            }

        }


    }

}
