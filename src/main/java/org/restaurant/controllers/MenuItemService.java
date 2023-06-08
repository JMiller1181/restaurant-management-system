package org.restaurant.controllers;

import org.restaurant.models.Inventory;
import org.restaurant.models.MenuItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuItemService {
    private List<MenuItem> menuList;

    public MenuItemService() {
        this.menuList = new ArrayList<>();
    }

    public MenuItem createNewMenuItem(){
        Scanner scanner = new Scanner(System.in);
        List<String> ingredientList = new ArrayList<>();
        System.out.println("What is the name of the item?");
        String name = scanner.nextLine();
        System.out.println("What is the item description");
        String desc = scanner.nextLine();
        System.out.println("How long does it take to prepare?");
        int prep = scanner.nextInt();
        scanner.nextLine();
        System.out.println("What is the price?");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("What ingredients does it take?");
        while(true){
            String ingredient = scanner.nextLine();
            if(ingredient == ""){
                break;
            } else {
                ingredientList.add(ingredient);
            }
        }
        MenuItem newItem = new MenuItem(name,desc,prep,price,ingredientList);
        return newItem;
    }
    public void addMenuItem(MenuItem item) {
        menuList.add(item);
        item.setItemID(menuList.indexOf(item) + 1);
    }

    public String printMenu() {
        String itemOptions = "";
        for (MenuItem item : menuList) {
            itemOptions += item.getItemID() + ") " + item.getName() + "\n";
        }
        itemOptions += "Select any other number to complete your order.";
        return itemOptions;
    }

    public List<MenuItem> getMenuList() {
        return menuList;
    }

    public void setMenuList() {
        if (readMenu() == null) {
            this.menuList = new ArrayList<>();
        } else {
            this.menuList = readMenu();
        }
    }

    public MenuItem findMenuItem(int IDNumber) {
        for (MenuItem item : menuList) {
            if (item.getItemID() == IDNumber) {
                return item;
            }
        }
        System.out.println("Order complete.");
        return null;
    }

    public void removeMenuItem(MenuItem item) {
        if (item != null) {
            menuList.remove(item);
        } else {
            System.out.println("Cannot remove non-existent item from menu.");
        }
    }

    public void updateMenu(List<MenuItem> menuList) {
        try {
            File menuFile = new File("src/main/java/org/restaurant/utils/foodMenu.txt");
            if (menuFile.exists()) {
                FileWriter writer = new FileWriter(menuFile);
                for(MenuItem item: menuList){
                    System.out.println(item.getName()+","+item.getItemID());
                    writer.write(item.getName()+","+item.getDescription()
                    +","+item.getPrepTime()+","+item.getPrice()+","+item.getItemID()+
                            ","+item.getIngredientList()+"\n");
                }
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<MenuItem> readMenu() {
        String name = "";
        String desc = "";
        int prep = 0;
        double price = 0;
        int itemID = 0;
        menuList.clear();

        try {
            File menuFile = new File("src/main/java/org/restaurant/utils/foodMenu.txt");
            if (menuFile.exists()) {
                    Scanner scanner = new Scanner(menuFile);
                    while (scanner.hasNextLine()) {
                        System.out.println("Running");
                        String item = scanner.nextLine();
                        String[] variables = item.split(",");
                        List<String> ingredients = new ArrayList<>();
                        name = variables[0];
                        desc = variables[1];
                        prep = Integer.parseInt(variables[2]);
                        price = Double.parseDouble(variables[3]);
                        itemID = Integer.parseInt(variables[4]);
                        for (int i = 5; i < variables.length; i++){
                            String ingredient = variables[i].replaceAll("[\\[\\]\\s]", "");
                            ingredients.add(ingredient);
                        }
                        MenuItem menuItem = new MenuItem(name,desc,prep,price,ingredients);
                        addMenuItem(menuItem);
                    }
                }
            return menuList;
        } catch (Exception e) {
            System.out.println("Menu is empty, please add items to the menu.");
            return null;
        }
    }

    public void changeMenuSwitch(Scanner scanner, MenuItemService menuItemService){
        System.out.println("""
                \n----- CHANGE MENU -----
                What would you like to do?
                1) Add an item
                2) Remove an item
                3) Go back\n""");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option){
            case 1:
                addMenuItem(createNewMenuItem());
                updateMenu(menuList);
                break;
            case 2:
                System.out.println("Which item would you like to remove?");
                System.out.println(printMenu());
                int removeID = scanner.nextInt();
                scanner.nextLine();
                removeMenuItem(findMenuItem(removeID));
                updateMenu(menuList);
                break;
            default:
                break;
        }
    }

}
