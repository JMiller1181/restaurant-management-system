package org.restaurant.controllers;

import org.restaurant.models.MenuItem;
import org.restaurant.models.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderService {
    private List<Order> orderList;
    private int totalOrders;

    public OrderService() {
        this.orderList = new ArrayList<>();
        this.totalOrders = 0;
    }

    /**
     * Sets order status to complete
     *
     * @param order the order to have its status changed
     */
    public void completeOrder(Order order) {
        order.setOrderStatus(Order.OrderStatus.COMPLETED);
        order.setLastHandled();

    }

    /**
     * Adds an order to the order list and sets its ID
     *
     * @param order the order to be added to the list of orders
     */
    public void addOrder(Order order) {
        orderList.add(order);
        totalOrders = orderList.size();
        order.setOrderID(totalOrders);
    }

    public void removeItemFromOrder(Order order, int itemID) {
        List<MenuItem> itemsOrdered = order.getItemsOrderedList();
        for (MenuItem item : itemsOrdered) {
            if (item.getItemID() == itemID) {
                itemsOrdered.remove(item);
            }
        }
    }

    /**
     * Creates a new order and adds it to the order list
     */
    public void createNewOrder() {
        Order order = new Order();
        addOrder(order);
        System.out.println("Your order number is: " + order.getOrderID());
    }

    /**
     * Sets the order status to preparing
     *
     * @param order the order to have its status changed
     */
    public void prepareOrder(Order order) {
        order.setOrderStatus(Order.OrderStatus.PREPARING);
        order.setLastHandled();
    }

    /**
     * @return the list of total orders
     */
    public List<Order> getOrderList() {
        return orderList;
    }

    /**
     * Finds the order by the order ID
     *
     * @param orderID the order ID
     * @return the order with the matching ID
     */
    public Order findOrder(int orderID) {
        return orderList.get(orderID - 1);
    }

    /**
     * Searches for all orders that have the matching status and returns a string of those orders
     *
     * @param status the status to search by
     * @return a string of all matching orders
     */
    public String findOrderByStatus(Order.OrderStatus status) {
        String foundOrders = "";
        for (Order order : orderList) {
            if (order.getOrderStatus() == status) {
                foundOrders += order + "\n";
            }
        }
        return foundOrders;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void orderServiceSwitch(Scanner scanner, OrderService orderService) {
        System.out.println("""
                \n----- ORDER MENU -----
                What would you like to do?
                1) Take an order
                2) Find an order
                3) Change an order
                4) Exit\n""");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                orderService.createNewOrder();
                break;
            case 2:
                System.out.println("""
                        How would you like to search?
                        1) By order status
                        2) By order ID
                        3) Go back""");
                break;
            case 3:
                System.out.println("""
                        What would you like to change?
                        1) Order status
                        2) Items ordered
                        3) Go back""");
                break;
            case 4:
                break;
        }
    }

    public static void main(String[] args) {
//        OrderService orderService = new OrderService();
//        MenuItemService menuItemService = new MenuItemService();
////        menuItemService.setMenuList();
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("What would you like to order?");
//        int item = scanner.nextInt();
//        orderService.createNewOrder();
//        orderService.findOrder(1).addItemsOrdered(menuItemService.findMenuItem(item));
//        System.out.println("Here is your current order");
//        System.out.println(orderService.findOrder(1));
//        orderService.prepareOrder(orderService.findOrder(1));
//        System.out.println(orderService.findOrder(1));
//        System.out.println(orderService.findOrder(1).getOrderedItems());
//        orderService.completeOrder(orderService.findOrder(1));
//        System.out.println("Search for orders by status");
//        String statusString = scanner.nextLine();
//        if(statusString.equalsIgnoreCase("complete")){
//            System.out.println(orderService.findOrderByStatus(Order.OrderStatus.COMPLETED));
//        } else if (statusString.equalsIgnoreCase("waiting")) {
//            System.out.println(orderService.findOrderByStatus(Order.OrderStatus.WAITING));
//        } else if (statusString.equalsIgnoreCase("preparing")) {
//            System.out.println(orderService.findOrderByStatus(Order.OrderStatus.PREPARING));
//        }
    }
}
