package org.restaurant.controllers;

import org.restaurant.models.MenuItem;
import org.restaurant.models.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderService {
    private List<Order> orderList;
    private int totalOrders;
    public OrderService(){
        this.orderList = new ArrayList<>();
        this.totalOrders = 0;
    }
    public void completeOrder(Order order){
        order.setOrderStatus(Order.OrderStatus.COMPLETED);
        order.setLastHandled();

    }
    public void addOrder(Order order){
        orderList.add(order);
        totalOrders = orderList.size();
        order.setOrderID(totalOrders);
    }

    public void createNewOrder(){
        Order order = new Order();
        addOrder(order);
        System.out.println("Your order number is: " + order.getOrderID());
    }

    public void prepareOrder(Order order){
        order.setOrderStatus(Order.OrderStatus.PREPARING);
        order.setLastHandled();
    }
    public List<Order> getOrderList() {
        return orderList;
    }
    public Order findOrder(int orderID){
        return orderList.get(orderID - 1);
    }
    public String findOrderByStatus(Order.OrderStatus status){
        String foundOrders = "";
        for (Order order: orderList){
            if(order.getOrderStatus() == status){
                foundOrders += order + "\n";
            }
        }
        return foundOrders;
    }
    public int getTotalOrders() {
        return totalOrders;
    }

    public static void main(String[] args){
        OrderService orderService = new OrderService();
        MenuItemService menuItemService = new MenuItemService();
        menuItemService.setMenuList();
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to order?");
        String item = scanner.nextLine();
        orderService.createNewOrder();
        orderService.findOrder(1).addItemsOrdered(menuItemService.findMenuItem(item));
        System.out.println("Here is your current order");
        System.out.println(orderService.findOrder(1));
        orderService.prepareOrder(orderService.findOrder(1));
        System.out.println(orderService.findOrder(1));
        System.out.println(orderService.findOrder(1).getOrderedItems());
        orderService.completeOrder(orderService.findOrder(1));
        System.out.println("Search for orders by status");
        String statusString = scanner.nextLine();
        if(statusString.equalsIgnoreCase("complete")){
            System.out.println(orderService.findOrderByStatus(Order.OrderStatus.COMPLETED));
        } else if (statusString.equalsIgnoreCase("waiting")) {
            System.out.println(orderService.findOrderByStatus(Order.OrderStatus.WAITING));
        } else if (statusString.equalsIgnoreCase("preparing")) {
            System.out.println(orderService.findOrderByStatus(Order.OrderStatus.PREPARING));
        }
    }
}
