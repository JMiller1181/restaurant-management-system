package org.restaurant.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.restaurant.models.User.Role;
import org.restaurant.utils.PasswordHasher;

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
                System.out.println("\nLogin Successful! \n" +
                        "Welcome " + colorCode + username.toUpperCase() + "\u001B[0m" + ", Role: " + role);
                return true;    //if username and pw match return true
            }
        }
//        System.out.println("Invalid username or password! Try again");
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
    private static boolean handleManagerMenu(Scanner scanner, UserService userService) {
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
                    break;
                case 2:
                    //order options
                    break;
                case 3:
                    //menu options
                    break;
                case 4:
                    //Inventory options
                    break;
                case 5:
                    //sales report options
                    break;
                case 6:
                    //switch user and go back to log in
                    System.out.println("Logging off...\n");
                    return false;
                case 7:
                    //end session
                    return true;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    //staff member switch statement options
    private static boolean handleStaffMenu(Scanner scanner, UserService userService) {
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
                    break;
                case 2:
                    //order options
                    break;
                case 3:
                    //switch user go back to log in
                    System.out.println("Logging off... ");
                    return false;
                case 4:
                    //end session
                    return true;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    //login screen
    private static Role loginScreen(UserService userService, Scanner scanner) {
        System.out.println("\n*** WELCOME, PLEASE SIGN IN ***\n");

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

    public static void main(String[] args) {
        // Create a PasswordHasher object
        PasswordHasher passwordHasher = new PasswordHasher();

        // Create a Scanner object for input
        Scanner scanner = new Scanner(System.in);

        // Create a UserService object with the employee and password lists
        UserService userService = new UserService();

        //login method
        Role userRole = loginScreen(userService, scanner);

        while (userRole != null) {
            if (userRole == Role.MANAGER) {
                boolean exitSession = handleManagerMenu(scanner, userService);
                if (exitSession) {
                    break;
                }
            } else if (userRole == Role.STAFF) {
                boolean exitSession = handleStaffMenu(scanner, userService);
                if (exitSession) {
                    break;
                }
            }
            userRole = loginScreen(userService, scanner);
        }
        System.exit(0);
    }

}

