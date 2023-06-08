package org.restaurant.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String description;
    private int prepTime;
    private double price;
    private int itemID;
    private List<String> ingredientList;

    public MenuItem(String name, String description, int prepTime, double price, List<String> ingredientList){
        this.name = name;
        this.description = description;
        this.prepTime = prepTime;
        this.price = price;
        this.ingredientList = ingredientList;
        this.itemID = 0;
    }

    public MenuItem(String name, String description, int prepTime, double price){
        this.name = name;
        this.description = description;
        this.prepTime = prepTime;
        this.price = price;
        this.ingredientList = new ArrayList<>();
        this.itemID = 0;

    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Adds an ingredient to the ingredientList
     * @param ingredient string of ingredient to be added
     */
    public void addIngredientToList(String ingredient) {
        this.ingredientList.add(ingredient);

    }

    public int getItemID() {
        return itemID;
    }

    @Override
    public String toString() {
        return "\nName: " + name + "\n" +
                "Item ID: " + itemID + "\n" +
                "Description: " + description + "\n" +
                "Price: " + price + "\n" +
                "Ingredients: " + ingredientList;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public List<String> getIngredientList() {
        for(String ingredient: ingredientList){
            System.out.println(ingredient);
        }
        return ingredientList;
    }

    public String getDescription() {
        return description;
    }
    public int getPrepTime() {
        return prepTime;
    }


}
