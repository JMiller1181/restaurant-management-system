package org.restaurant.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Order {
    private double totalPrice;
    private int orderID;
    private List<MenuItem> itemsOrdered;
    private OrderStatus orderStatus;
    private HashMap<String, Integer> quantityOrdered;
    private Date lastHandled;
    public enum OrderStatus{
        WAITING, PREPARING, COMPLETED
    }
    public Order(){
        this.totalPrice = 0;
        this.orderID = 0;
        this.orderStatus = OrderStatus.WAITING;
        this.itemsOrdered = new ArrayList<>();
        this.quantityOrdered = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.lastHandled = new Date();
        formatter.format(lastHandled);
    }

    public void setLastHandled() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.lastHandled = new Date();
        formatter.format(lastHandled);

    }

    public void setOrderID(int totalOrders) {
        orderID = totalOrders;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void addItemsOrdered(MenuItem item) {
        totalPrice = 0;
        itemsOrdered.add(item);
        getTotalPrice();
    }

    public void setTotalPrice() {
        for(MenuItem item: itemsOrdered){
            totalPrice += item.getPrice();
        }
    }

    public int getOrderID() {
        return orderID;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<MenuItem> getItemsOrderedList() {
        return itemsOrdered;
    }
    public String getOrderedItems(){
        List<MenuItem> itemsOrdered = getItemsOrderedList();
        String orderList = "";
        for(MenuItem item: itemsOrdered){
            orderList += item + "\n";
        }
        return orderList;
    }
    public double getTotalPrice() {
        setTotalPrice();
        return totalPrice;
    }

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
