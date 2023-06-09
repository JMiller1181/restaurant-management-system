package org.restaurant.models;

import org.restaurant.controllers.MenuItemService;

import java.io.*;
import java.util.*;

public class Inventory {
    private HashMap<String, Integer> ingredients;
    private Scanner scanner;

    public Inventory() {
        ingredients = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    // Add ingredients
    public void addIngredient(String ingredient, int amount) {
        if (!ingredients.containsKey(ingredient)) {
            ingredients.put(ingredient, amount);
        } else {
            ingredients.put(ingredient, ingredients.get(ingredient) + amount);
        }
    }

    // Get amount of ingredients in inventory
    public int getInventory(String ingredient) {
        if (ingredients.containsKey(ingredient)) {
            return ingredients.get(ingredient);
        } else {
            return 0;
        }
    }

    // Update amount of ingredients in inventory
    public void updateInventory(String ingredient, int amount) {
        if (ingredients.containsKey(ingredient)) {
            ingredients.put(ingredient, amount);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String ingredient : ingredients.keySet()) {
            int amount = getInventory(ingredient);
            sb.append(ingredient).append(": ").append(amount).append("\n");
        }
        return sb.toString();
    }

    // Read Inventory from file
    public static Inventory readInventoryFromFile(String filename) {
        Inventory inventory = new Inventory();
        File file = new File(filename);

        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String ingredient = parts[0];
                        int amount = Integer.parseInt(parts[1]);
                        inventory.addIngredient(ingredient, amount);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Error reading inventory file: " + e.getMessage());
            }
        }

        return inventory;
    }

    // Write inventory to file
    private static void writeInventoryToFile(Inventory inventory, String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (String ingredient : inventory.ingredients.keySet()) {
                int amount = inventory.getInventory(ingredient);
                writer.println(ingredient + ":" + amount);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error writing inventory file: " + e.getMessage());
        }
    }

    // Print low inventory WARNING
    private void printWarning(String ingredient, int amount) {
        System.out.println("\u001B[31mWARNING\u001B[0m : Low Inventory for " + ingredient + ". Only " + amount + " left!");
    }

    // Process orders
    public void processOrder(List<String> orderedIngredients) {
        for (String ingredient : orderedIngredients) {
            updateInventory(ingredient, getInventory(ingredient) - 1);
        }
        writeInventoryToFile(this,"src/main/java/org/restaurant/utils/inventory.txt");
    }

    // Show inventory with warnings for low ingredients
    private void viewInventory() {
        System.out.println("\nInventory:");
        for (String ingredient : ingredients.keySet()) {
            int amount = getInventory(ingredient);
            System.out.println(ingredient + ": " + amount);
            if (amount <= 3) {
                printWarning(ingredient, amount);
            }
        }
    }

    // Update ingredient amount
    private void updateIngredient() {
        System.out.print("Enter ingredient name: ");
        String ingredient = scanner.nextLine();
        System.out.print("Enter new amount: ");
        int amount = Integer.parseInt(scanner.nextLine());
        updateInventory(ingredient, amount);
        writeInventoryToFile(this, "src/main/java/org/restaurant/utils/inventory.txt");
        System.out.println("Inventory updated successfully!");
    }

    // Main menu
    public void handleInventoryMenu() {
        System.out.println("\n----- Welcome to the Inventory Management -----");
        while (true) {
            System.out.println("\n----- Inventory Main Menu -----");
            System.out.println("1. View Inventory");
            System.out.println("2. Update Ingredient Amount");
            System.out.println("3. Go back");

            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            // Read inventory from file
            Inventory inventory = readInventoryFromFile("src/main/java/org/restaurant/utils/inventory.txt");
            switch (choice) {
                case 1:
                    viewInventory();

                    // Print current inventory
                    System.out.println("Current inventory:");
                    System.out.println(inventory);

                    // Check the amount of each ingredient and warn if it is less than 3.
                    for (String ingredient : inventory.ingredients.keySet()) {
                        int amount = inventory.getInventory(ingredient);
                        if (amount <= 3) {
                            // Print a warning message in red.
                            inventory.printWarning(ingredient, amount);
                        }
                    }

//                    // Sample order
//                    List<String> orderedIngredients = Arrays.asList("buns", "patties", "chicken", "veggie", "buns", "ketchup", "pickles");
//                    inventory.processOrder(orderedIngredients);
//
//                    // Print inventory after order
//                    System.out.println("\nInventory after sample order:");
//                    System.out.println(inventory);

                    // Check the amount of each ingredient and warn if it is less than 3.
                    for (String ingredient : inventory.ingredients.keySet()) {
                        int amount = inventory.getInventory(ingredient);
                        if (amount <= 3) {
                            // Print a warning message in red.
                            inventory.printWarning(ingredient, amount);
                        }
                    }

                    // Write inventory to file
                    writeInventoryToFile(inventory, "src/main/java/org/restaurant/utils/inventory.txt");

                    // Show main menu
                    inventory.handleInventoryMenu();

                    break;
                case 2:
                    updateIngredient();
                    break;
                case 3:
                    System.out.println("Exiting Inventory Management");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");

            }

        }
    }
}








