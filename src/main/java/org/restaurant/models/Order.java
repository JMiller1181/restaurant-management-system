package org.restaurant.models;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Order {
    private double totalPrice;
    private int orderID;
    private List<MenuItem> itemsOrdered;
    private OrderStatus orderStatus;
    private HashMap<String, Integer> quantityOrdered;
    private LocalDateTime lastHandled;
    private int orderTableID;
    public enum OrderStatus{
        WAITING, PREPARING, COMPLETED
    }

    public Order(){
        this.totalPrice = 0;
        this.orderID = 0;
        this.orderStatus = OrderStatus.WAITING;
        this.itemsOrdered = new ArrayList<>();
        this.quantityOrdered = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.lastHandled = LocalDateTime.now();
        formatter.format(lastHandled);
        this.orderTableID = 0;
    }

    /**
     * Sets the orders tableID by getting the table ID
     * @param orderTableID the table ID of the table the customer is sitting at
     */
    public void setOrderTableID(int orderTableID) {
        this.orderTableID = orderTableID;
    }

    /**
     *
     * @return the orders table ID
     */
    public int getOrderTableID() {
        return orderTableID;
    }

    /**
     * updates the time on the order
     */
    public void setLastHandled() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.lastHandled = LocalDateTime.now();
        formatter.format(lastHandled);

    }

    /**
     * Sets the order ID equal to the total number of orders that have been taken
     * @param totalOrders the total number of orders that have been taken
     */
    public void setOrderID(int totalOrders) {
        orderID = totalOrders;
    }

    /**
     * Sets the order status
     * @param orderStatus waiting, preparing, or complete
     */
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * Adds an item to the order and recalculates the price
     * @param item the item being added to the order
     */
    public void addItemsOrdered(MenuItem item) {
        totalPrice = 0;
        itemsOrdered.add(item);
        getTotalPrice();
    }

    /**
     * sets the total price equal to the total cost of items ordered
     */
    public void setTotalPrice() {
        for(MenuItem item: itemsOrdered){
            totalPrice += item.getPrice();
        }
    }

    /**
     *
     * @return the order ID
     */
    public int getOrderID() {
        return orderID;
    }

    /**
     *
     * @return the order status
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     *
     * @return the list of the items ordered
     */
    public List<MenuItem> getItemsOrderedList() {
        return itemsOrdered;
    }

    /**
     * Gets the items in the order and adds them to a string
     * @return all ordered items in a string
     */
    public String getOrderedItems(){
        List<MenuItem> itemsOrdered = getItemsOrderedList();
        String orderList = "";
        for(MenuItem item: itemsOrdered){
            orderList += item + "\n";
        }
        return orderList;
    }

    /**
     * Adds up the total price and returns it
     * @return the total price of the order
     */
    public double getTotalPrice() {
        totalPrice = 0;
        setTotalPrice();
        return totalPrice;
    }

    /**
     * Keeps track of how many of each item are in the order in a map and returns a string of that info
     * @return a String of all the items and their quantities
     */
    public String addItemQuantity(){
        String orderInfo = "\n";
        quantityOrdered.clear();
        for(MenuItem item: itemsOrdered){
            if(!quantityOrdered.containsKey(item.getName())){
                quantityOrdered.put(item.getName(),1);
            } else {
                quantityOrdered.put(item.getName(), quantityOrdered.get(item.getName()) + 1);
            }
        }
        for(MenuItem item: itemsOrdered){
            if(!orderInfo.contains(item.getName())){
                orderInfo += item.getName() + "->" + quantityOrdered.get(item.getName()) + "\n";
            }
        }
        return orderInfo;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderID + "\n"
                + "Order: " + addItemQuantity() + "\n"
                + "Bill: " + totalPrice + "\n"
                + "Order Status: " + orderStatus + " at " + lastHandled;
    }
}
