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

    public void addMenuItem(MenuItem item) {
        menuList.add(item);
        item.setItemID(menuList.size());
    }

    public String printMenu() {
        String itemOptions = "";
        for (MenuItem item : menuList) {
            itemOptions += item.getItemID() + ") " + item.getName() + "\n";
        }
        itemOptions += "Press any other key to exit";
        return itemOptions;
    }

    public List<MenuItem> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuItem> itemList) {
//        if (readMenu() == null) {
//            this.menuList = new ArrayList<>();
//        } else {
//            this.menuList = readMenu();
//        }
    }

    public MenuItem findMenuItem(int IDNumber) {
        for (MenuItem item : menuList) {
            if (item.getItemID() == IDNumber) {
                return item;
            }
        }
        System.out.println("No such item on the menu.");
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
                    writer.write("name:"+item.getName()+",description:"+item.getDescription()
                    +",prepTime:"+item.getPrepTime()+",price:"+item.getPrice()+",itemID:"+item.getItemID()+
                            ",ingredients:"+item.getIngredientList());
                    writer.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> readMenu() {
        List<String> menuItems = new ArrayList<>();
        try {
            File menuFile = new File("src/main/java/org/restaurant/utils/foodMenu.txt");
            if (menuFile.exists()) {
                if (menuList.isEmpty()) {
                    System.out.println("Menu is empty, please add items to the menu.");
                    return null;
                } else {
                    Scanner scanner = new Scanner(menuFile);
                    while (scanner.hasNextLine()) {
                        String item = scanner.nextLine();
                        String[] variables = item.split(",");
                        for (String var : variables) {
                            String[] parts = var.split(":");
                            String property = parts[0];
                            switch (property) {
                                case "name":
                                    String name = parts[1];
                                    break;
                                case "description":
                                    String desc = parts[1];
                                    break;
                                case "prepTime":
                                    int prep = Integer.parseInt(parts[1]);
                                    break;
                                case "price":
                                    double price = Double.parseDouble(parts[1]);
                                    break;
                                case "itemID":
                                    int itemID = Integer.parseInt(parts[1]);
                                    break;
                                case "ingredients":
                            }
                        }
                    }
                }
            }
            return menuItems;
        } catch (Exception e) {
            System.out.println("Menu is empty, please add items to the menu.");
            return null;
        }
    }
        ////////////////////////
    public static void main(String[] args){
        MenuItemService menuItemService = new MenuItemService();
        List<String> ingredients = new ArrayList<>();
        ingredients.add("buns");
        ingredients.add("patties");
        MenuItem item = new MenuItem("item1", "the first item", 2, 2, ingredients);
        List<MenuItem> menu = new ArrayList<>();
        menu.add(item);
        menuItemService.setMenuList(menu);
        menuItemService.updateMenu(menu);
//        Scanner scanner = new Scanner(System.in);
//        menuItemService.setMenuList();
//        System.out.println(menuItemService.getMenuList());
//        menuItemService.removeMenuItem(menuItemService.getMenuList().remove(1));
//        System.out.println(menuItemService.getMenuList());
//        System.out.println("What is the item name?");
//        String itemName = scanner.nextLine();
//        System.out.println("What is the item description?");
//        String itemDescription = scanner.nextLine();
//        System.out.println("How long to prep item?");
//        int itemPrep = scanner.nextInt();
//        scanner.nextLine();
//        System.out.println("What is the price?");
//        double itemPrice = scanner.nextDouble();
//        scanner.nextLine();
//        MenuItem item = new MenuItem(itemName, itemDescription, itemPrep, itemPrice);
//        for(int ing = 0; ing < 4; ing++){
//            System.out.println("What ingredients make up the item?");
//            String ingredient = scanner.nextLine();
//            item.addIngredientToList(ingredient);
//        }
//        menuItemService.addMenuItem(item);
//        menuItemService.updateMenu(menuItemService.getMenuList());
//        menuItemService.setMenuList();
//        System.out.println(menuItemService.getMenuList());
//        System.out.println("What item are you looking for?");
//        String search = scanner.nextLine();
//        System.out.println(menuItemService.findMenuItem(search));
    }
}
