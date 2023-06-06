package org.restaurant.controllers;

import org.restaurant.models.MenuItem;
import org.restaurant.models.Table;
import org.restaurant.models.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class SalesReport {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  //formatter for file name and exact date and time
    private static final DecimalFormat decimalFormatter = new DecimalFormat("0.00");    //formatting money
    private Map<MenuItem, Integer> menuItem;
    private Map<Table, Double> tables;
    private User user;

    public SalesReport() {
    }

    public SalesReport(Map<MenuItem, Integer> menuItem, Map<Table, Double> tables, User user) {
        this.menuItem = menuItem;
        this.tables = tables;
        this.user = user;
    }

//    private double getTotalSalesRevenue() {
//
//    }
//
//    private Map<MenuItem, Integer> getPopularItems() {
//
//    }
//
//    private Map<Table, Double> getTableOrders() {
//
//    }

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
                    "Total Revenue: $\n\n" +
                    "Most Popular Items: \n" +
                    "1. \n" +
                    "2. \n" +
                    "3. \n\n" +
                    "Tables Sales: \n" +
                    "1. \n" +
                    "2. \n" +
                    "3. \n\n" +
                    "Detailed Orders: \n" +
                    "Items: \n" +
                    "   - \n" +
                    "   - \n\n" +
                    "Total: \n\n" +
                    "Order ID: \n" +
                    "Table ID: \n" +
                    "Items: \n" +
                    "   - \n" +
                    "   - \n" +
                    "Total: " );

            writer.flush();     //flush to access right away - without having to rerun, jut when you exit from menu

            System.out.println("Sales Report printed to a new file successfully!");

        } catch (IOException e) {
            System.out.println("Unable to print report. " + e.getMessage());
        }
    }

    private void printSalesReport() {
        LocalDateTime current = LocalDateTime.now();

        //printing to console - same as export but with *color*
        System.out.println("-----------------------------\n Daily Sales Report \n " + "\u001B[34m" +
                dateTimeFormatter.format(current) + "\n-----------------------------\n \u001B[0m"
                +
                "Total Revenue: $\n\n" +
                "Most Popular Items: \n" +
                "\u001B[33m1.\u001B[0m\t more things here \n" +
                "\u001B[33m2.\u001B[0m\t \n" +
                "\u001B[33m3.\u001B[0m\t \n\n" +
                "Tables Sales: \n" +
                "\u001B[33m1.\u001B[0m\t \n" +
                "\u001B[33m2.\u001B[0m\t \n" +
                "\u001B[33m3.\u001B[0m\t \n\n" +
                "Detailed Orders: \n" +
                "Items: \n" +
                "\u001B[33m   - \u001B[0m\t \n" +
                "\u001B[33m   - \u001B[0m\t \n\n" +
                "Total: \n\n" +
                "Order ID: \n" +
                "Table ID: \n" +
                "Items: \n" +
                "\u001B[33m   -\u001B[0m\t \n" +
                "\u001B[33m   -\u001B[0m\t \n" +
                "Total: ");
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
                    3. Export report to a new file and print report to console
                    4. Exit""");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> report.exportDailySalesReport();
                case 2 -> report.printSalesReport();
                case 3 -> {
                    report.exportDailySalesReport();
                    report.printSalesReport();
                }
                case 4 -> {
                    System.out.println("Good-bye!");
                    exit = true;
                }
                default -> System.out.println("Invalid entry, try again");
            }

        }


    }

}
