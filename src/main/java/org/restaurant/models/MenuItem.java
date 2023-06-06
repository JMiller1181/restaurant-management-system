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
    private List<String> ingredientList;

    public MenuItem(String name, String description, int prepTime, double price, List<String> ingredientList){
        this.name = name;
        this.description = description;
        this.prepTime = prepTime;
        this.price = price;
        this.ingredientList = ingredientList;
    }

    public MenuItem(String name, String description, int prepTime, double price){
        this.name = name;
        this.description = description;
        this.prepTime = prepTime;
        this.price = price;
        this.ingredientList = new ArrayList<>();
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

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
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
        return ingredientList;
    }

    public String getDescription() {
        return description;
    }
    public int getPrepTime() {
        return prepTime;
    }


}
