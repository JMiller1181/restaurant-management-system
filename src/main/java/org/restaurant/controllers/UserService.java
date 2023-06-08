package org.restaurant.controllers;

import org.restaurant.models.Inventory;
import org.restaurant.models.Table;
import org.restaurant.models.User.Role;
import org.restaurant.utils.PasswordHasher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.restaurant.controllers.SalesReport.handleSalesReportMenu;
import static org.restaurant.models.Table.createTables;
import static org.restaurant.models.Table.handleTableMenu;

public class UserService {
    private List<String> employee;
    private List<String> password;
    private static Scanner scanner;

    public UserService() {
        employee = new ArrayList<>();
        password = new ArrayList<>();
        initializeUsers();
    }

    public UserService(List<String> employee, List<String> password, Scanner scanner) {
        this.employee = employee;
        this.password = password;
        this.scanner = scanner;
    }

    private void initializeUsers() {
        List<String> employee = new ArrayList<>();
        List<String> password = new ArrayList<>();

        // Add employee usernames and hashed passwords to the lists
        employee.add("Javier(STAFF)");
        employee.add("Darranda(STAFF)");
        employee.add("Emily(STAFF)");
        employee.add("Jacob(MANAGER)");

        // Hash the password before storing
        password.add(PasswordHasher.hash("jav123"));
        password.add(PasswordHasher.hash("dar123"));
        password.add(PasswordHasher.hash("emi123"));
        password.add(PasswordHasher.hash("jac123"));

        this.employee = employee;
        this.password = password;
    }

    public boolean login(String username, String inputPassword) {   //boolean for easier testing - returns true if login is correct
        for (int i = 0; i < employee.size(); i++) {     //iterating through employee array looking for matching username and pw
            List<String> employeeDetails = List.of(employee.get(i).split("\\("));   //splitting the employees with their names and roles
            String uname = employeeDetails.get(0);      //assigns the username from the list that was split
            Role role = Role.valueOf(employeeDetails.get(1).replace(")", ""));  //assigns the role referencing the user enum

            if (username.equalsIgnoreCase(uname) && PasswordHasher.check(inputPassword, password.get(i))) {    //checking if the uname matches corresponding pw using PasswordHasher.check
                String colorCode = (role == Role.MANAGER) ? "\u001B[34m" : "\u001B[35m";    //assigning blue to manager and magenta to staff
                System.out.println("\n \u001B[32m\tLogin Successful! \u001B[0m \n" +
                        "Welcome " + colorCode + username.toUpperCase() + "\u001B[0m" + ", Role: " + role);
                return true;    //if username and pw match return true
            }
        }
        return false;   //else false
    }

    private static Role getUserRole(UserService userService, String username) {
        for (int i = 0; i < userService.employee.size(); i++) {     //going through the list of employees
            List<String> employeeDetails = List.of(userService.employee.get(i).split("\\("));   //splitting by role
            String uname = employeeDetails.get(0);
            Role role = Role.valueOf(employeeDetails.get(1).replace(")", ""));  //replace end parenthesis with space
            if (username.equalsIgnoreCase(uname)) {     //assigning role from uname
                return role;
            }
        }
        return null; // User not found
    }

    //manager switch statment options
    public static boolean handleManagerMenu(Scanner scanner, UserService userService) {
        Table[] tables = createTables();
        OrderService orderService = new OrderService();
        Inventory inventory = Inventory.readInventoryFromFile("src/main/java/org/restaurant/utils/inventory.txt");
        SalesReport reports = new SalesReport();
        MenuItemService menuItemService = new MenuItemService();
        menuItemService.setMenuList();
        while (true) {
            System.out.println("""

                    Hi \u001B[34mMANAGER\u001B[0m, please select from the items below:\s
                    1. Table Options
                    2. Order Options
                    3. Menu Options
                    4. Inventory Option
                    5. Sales Report
                    6. Log Off
                    7. End Session""");

            int managerChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (managerChoice) {
                case 1:
                    //tables options
                    handleTableMenu(scanner, tables);
                    break;
                case 2:
                    //order options
                    orderService.orderServiceSwitch(scanner,inventory, orderService, menuItemService);
                    break;
                case 3:
                    //menu options
                    menuItemService.changeMenuSwitch(scanner, menuItemService);
                    break;
                case 4:
                    //inventory options
                    inventory.handleInventoryMenu();
                    break;
                case 5:
                    //sales report options
                    handleSalesReportMenu(scanner, reports, orderService);
                    break;
                case 6:
                    //switch user and go back to log in
                    System.out.println("\u001B[37mLogging off...\u001B[0m\n");
                    return false;
                case 7:
                    //end session
                    System.out.println("\u001B[37mEnding Java Session, good-bye!\u001B[0m");
                    return true;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    //staff member switch statement options
    public static boolean handleStaffMenu(Scanner scanner, UserService userService) {
        Table[] tables = createTables();
        OrderService orderService = new OrderService();
        Inventory inventory = new Inventory();
        MenuItemService menuItemService = new MenuItemService();
        menuItemService.setMenuList();
        while (true) {
            System.out.println("""

                    Hi \u001B[35mSTAFF\u001B[0m, please select from the items below:\s
                    1. Table Options
                    2. Order Options
                    3. Log Off
                    4. End Session""");

            int staffChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (staffChoice) {
                case 1:
                    //table options
                    handleTableMenu(scanner, tables);
                    break;
                case 2:
                    //order options
                    orderService.orderServiceSwitch(scanner, inventory, orderService, menuItemService);
                    break;
                case 3:
                    //switch user go back to log in
                    System.out.println("\u001B[37mLogging off... \u001B[0m");
                    return false;
                case 4:
                    //end session
                    System.out.println("\u001B[37mEnding Java Session, good-bye!\u001B[0m");
                    return true;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    //login screen
    public static Role loginScreen(UserService userService, Scanner scanner) {
        System.out.println("\n \u001B[36m \u273E \u273E \u001B[36mWELCOME TO THE BURGER RESTAURANT \u273E \u273E \u001B[0m \n");

        boolean loggedIn = false;
        Role userRole = null; // Added variable to store the user role
        while (!loggedIn) {
            System.out.println("What is your username?");
            String username = scanner.nextLine();
            System.out.println("What is your password?");
            String pw = scanner.nextLine();
            loggedIn = userService.login(username, pw);
            if (loggedIn) {
                userRole = getUserRole(userService, username); // Get the user role
            } else {
                System.out.println("Invalid username or password, try again.\n");
            }
        }
        return userRole;
    }

}

