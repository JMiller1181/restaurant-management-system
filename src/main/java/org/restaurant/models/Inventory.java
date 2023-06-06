package org.restaurant.models;


import java.io.*;
import java.util.*;

public class Inventory {
    private HashMap<String, Integer> ingredients;

    public Inventory() {
        ingredients = new HashMap<>();
    }

    //Add ingredients
    public void addIngredient(String ingredient, int amount) {
        if (!ingredients.containsKey(ingredient)) {
            ingredients.put(ingredient, amount);
        } else {
            ingredients.put(ingredient, ingredients.get(ingredient) + amount);
        }
    }

    //Get amount of ingredients in inventory
    public int getInventory(String ingredient) {
        if (ingredients.containsKey(ingredient)) {
            return ingredients.get(ingredient);
        } else {
            return 0;
        }
    }

    //Update amount of ingredients in inventory
    public void updateInventory(String ingredient, int amount) {
        if (ingredients.containsKey(ingredient)) {
            ingredients.put(ingredient, amount);
        }
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "ingredients=" + ingredients +
                '}';
    }

    //Read Inventory from file
    private static Inventory readInventoryFromFile(String filename) {
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

    //Write inventory to file
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

    //Print low inventory WARNING
    private static void printWarning(String ingredient, int amount) {
        System.out.println("\u001B[31mWARNING\u001B[0m : Low Inventory for " + ingredient + ". Only " + amount + " left!");
    }

    //Process orders
    private static void processOrder(Inventory inventory, List<String> orderedIngredients) {
        for (String ingredient : orderedIngredients) {
            inventory.updateInventory(ingredient, inventory.getInventory(ingredient) - 1);
        }
    }

    public static void main(String[] args) {
        // Read inventory from file
        Inventory inventory = readInventoryFromFile("src/main/java/org/restaurant/utils/inventory.txt");

        // Print current inventory
        System.out.println("Current inventory:");
        System.out.println(inventory);


        // Check the amount of each ingredient and warn if it is less than 5.
        for (String ingredient : inventory.ingredients.keySet()) {
            int amount = inventory.getInventory(ingredient);
            if (amount <= 3) {
                // Print a warning message in red.
                printWarning(ingredient, amount);
            }
        }

        // Sample order
        List<String> orderedIngredients = Arrays.asList("buns", "patties", "chicken", "veggie", "buns", "ketchup", "pickles");
        processOrder(inventory, orderedIngredients);

        // Print inventory after order
        System.out.println("\nInventory after order:");
        System.out.println(inventory);

        // Update inventory
        // inventory.updateInventory("buns", 12);
        //inventory.updateInventory("patties", 0);
        // inventory.updateInventory("chicken", 3);
        // inventory.updateInventory("veggie", 9);

        // Print updated inventory
        System.out.println("\nUpdated inventory:");
        System.out.println(inventory);

        // Write inventory to file
        writeInventoryToFile(inventory, "src/main/java/org/restaurant/utils/inventory.txt");
    }
}







