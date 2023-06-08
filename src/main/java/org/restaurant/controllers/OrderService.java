package org.restaurant.controllers;

import org.restaurant.models.Inventory;
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
            } else{
                System.out.println("No such item in order.");
            }
        }
    }

    /**
     * Creates a new order and adds it to the order list
     */
    public Order createNewOrder() {
        Order order = new Order();
        addOrder(order);
        System.out.println("Your order number is: " + order.getOrderID());
        return order;
    }

    /**
     * Sets the order status to preparing
     *
     * @param order the order to have its status changed
     */
    public void prepareOrder(Order order, Inventory inventory) {
        order.setOrderStatus(Order.OrderStatus.PREPARING);
        List<MenuItem> itemsOrdered = order.getItemsOrderedList();
        for(MenuItem item: itemsOrdered){
            inventory.processOrder(item.getIngredientList());
        }
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
        if( orderList.get(orderID -1) != null){
            return orderList.get(orderID - 1);
        } else {
            System.out.println("No order with that ID exists.");
            return null;
        }
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
        if(foundOrders.length() < 1){
            foundOrders = "No orders of that status were found.";
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
                orderService.takeOrder();
                break;
            case 2:
                System.out.println("""
                        How would you like to search?
                        1) By order status
                        2) By order ID
                        3) Go back""");
                int searchType = scanner.nextInt();
                scanner.nextLine();
                searchOrderSwitch(searchType);
                break;
            case 3:
                System.out.println("What is the order number?");
                int selectedOrder = scanner.nextInt();
                scanner.nextLine();
                System.out.println("""
                        What would you like to change?
                        1) Order status
                        2) Items ordered
                        3) Go back""");
                int changeOrder = scanner.nextInt();
                scanner.nextLine();
                editOrderSwitch(selectedOrder,changeOrder);
                break;
            case 4:
                break;
        }
    }

    public void takeOrder(){
        Scanner scanner = new Scanner(System.in);
        MenuItemService menuItemService = new MenuItemService();
        menuItemService.setMenuList();
        Order newOrder = createNewOrder();
        while (true){
            System.out.println("What would you like to order?");
            System.out.println(menuItemService.printMenu());
            int option = scanner.nextInt();
            scanner.nextLine();
            if (menuItemService.findMenuItem(option) != null){
                newOrder.addItemsOrdered(menuItemService.findMenuItem(option));
            } else {
                break;
            }
            System.out.println(newOrder);
        }
        System.out.println("Your order is: " + newOrder);
    }

    public void searchOrderSwitch(int option){
        Scanner scanner = new Scanner(System.in);
        switch (option){
            case 1:
                System.out.println("""
                        What is the status of the order?
                        1) Waiting
                        2) Preparing
                        3) Complete
                        Select any other option to go back""");
                int selectedStatus = scanner.nextInt();
                scanner.nextLine();
                switch (selectedStatus){
                    case 1:
                        System.out.println(findOrderByStatus(Order.OrderStatus.WAITING));
                        break;
                    case 2:
                        System.out.println(findOrderByStatus(Order.OrderStatus.PREPARING));
                        break;
                    case 3:
                        System.out.println(findOrderByStatus(Order.OrderStatus.COMPLETED));
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                System.out.println("What is the order ID?");
                int IDNum = scanner.nextInt();
                scanner.nextLine();
                System.out.println(findOrder(IDNum));
                break;
            default:
                break;
        }
    }

    public void editOrderSwitch(int order, int option){
        Inventory inventory = new Inventory();
        Scanner scanner = new Scanner(System.in);
        MenuItemService menuItemService = new MenuItemService();
        menuItemService.setMenuList();
        switch (option) {
            case 1:
                System.out.println("""
                        What would you like to change the status to?
                        1) Preparing
                        2) Complete
                        Select any other number to go back""");
                int selectedStatus = scanner.nextInt();
                scanner.nextLine();
                switch (selectedStatus) {
                    case 1:
                        prepareOrder(findOrder(order), inventory);
                        break;
                    case 2:
                        completeOrder(findOrder(order));
                        break;
                    default:
                        break;
                }
                break;
            case 2:
                System.out.println("""
                        Would you like to add or remove items?
                        1) Add items
                        2) Remove items
                        Select any other number to go back""");
                int itemOption = scanner.nextInt();
                scanner.nextLine();
                switch (itemOption) {
                    case 1:
                        while (true) {
                            System.out.println("What would you like to order?");
                            System.out.println(menuItemService.printMenu());
                            int itemSelection = scanner.nextInt();
                            scanner.nextLine();
                            if (menuItemService.findMenuItem(itemSelection) != null) {
                                findOrder(order).addItemsOrdered(menuItemService.findMenuItem(option));
                            } else {
                                break;
                            }
                            System.out.println(findOrder(order));
                        }
                        System.out.println("Your order is: " + findOrder(order));
                        break;
                    case 2:
                        while (true) {
                            System.out.println("""
                            What is the ID of the item you would like to remove?
                            Press 0 to go back""");
                            int removeID = scanner.nextInt();
                            scanner.nextLine();
                            if(removeID == 0){
                                break;
                            } else {
                                removeItemFromOrder(findOrder(order), removeID);
                                System.out.println(menuItemService.findMenuItem(removeID).getName() +
                                        " removed from order.\nWould you like to remove another item?\n" +
                                        "1) Yes\n2) No");
                                int removeAgain = scanner.nextInt();
                                scanner.nextLine();
                                if(removeAgain != 1){
                                    System.out.println("Your order is: " + findOrder(order));
                                    break;
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrderService orderService = new OrderService();
        orderService.orderServiceSwitch(scanner, orderService);
    }
}
