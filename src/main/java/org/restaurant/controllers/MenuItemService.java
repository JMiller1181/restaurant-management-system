package org.restaurant.controllers;

import org.restaurant.models.MenuItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuItemService {
    private List<MenuItem> menuList;
    public MenuItemService(){
        this.menuList = new ArrayList<>();
    }
    public void addMenuItem(MenuItem item){
        menuList.add(item);
    }

    public List<MenuItem> getMenuList() {
        return menuList;
    }

    public void setMenuList() {
        this.menuList = readMenu();
    }

    public MenuItem findMenuItem(String itemName){
        for(MenuItem item: menuList){
            if(item.getName().equalsIgnoreCase(itemName)){
                return item;
            }
        }
        System.out.println("No such item on the menu.");
        return null;
    }
    public void removeMenuItem(MenuItem item){
        if (item != null){
            menuList.remove(item);
        } else {
            System.out.println("Cannot remove non-existent item from menu.");
        }
    }
    public void updateMenu(List<MenuItem> menuList){
        try{
            File menuFile = new File("src/main/java/org/restaurant/utils/foodMenu.txt");
            if(menuFile.exists()){
                FileOutputStream fileOut = new FileOutputStream(menuFile);
                ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                objectOut.writeObject(menuList);
                objectOut.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public List<MenuItem> readMenu(){
        try {
            FileInputStream fileIn = new FileInputStream("src/main/java/org/restaurant/utils/foodMenu.txt");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object object = objectIn.readObject();
            return (List<MenuItem>) object;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args){
        MenuItemService menuItemService = new MenuItemService();
        Scanner scanner = new Scanner(System.in);
        menuItemService.setMenuList();
        System.out.println(menuItemService.getMenuList());
        menuItemService.removeMenuItem(menuItemService.getMenuList().remove(1));
        System.out.println(menuItemService.getMenuList());
        System.out.println("What is the item name?");
        String itemName = scanner.nextLine();
        System.out.println("What is the item description?");
        String itemDescription = scanner.nextLine();
        System.out.println("How long to prep item?");
        int itemPrep = scanner.nextInt();
        scanner.nextLine();
        System.out.println("What is the price?");
        double itemPrice = scanner.nextDouble();
        scanner.nextLine();
        MenuItem item = new MenuItem(itemName, itemDescription, itemPrep, itemPrice);
        for(int ing = 0; ing < 4; ing++){
            System.out.println("What ingredients make up the item?");
            String ingredient = scanner.nextLine();
            item.addIngredientToList(ingredient);
        }
        menuItemService.addMenuItem(item);
        menuItemService.updateMenu(menuItemService.getMenuList());
        menuItemService.setMenuList();
        System.out.println(menuItemService.getMenuList());
        System.out.println("What item are you looking for?");
        String search = scanner.nextLine();
        System.out.println(menuItemService.findMenuItem(search));
    }
}
