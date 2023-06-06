package org.restaurant.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.restaurant.models.User.Role;
import org.restaurant.utils.PasswordHasher;

public class UserService {
    private List<String> employee;
    private List<String> password;
    private Scanner scanner;

    public UserService() {
        employee = new ArrayList<>();
        password = new ArrayList<>();
    }

    public UserService(List<String> employee, List<String> password, Scanner scanner) {
        this.employee = employee;
        this.password = password;
        this.scanner = scanner;
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

        System.out.println("Invalid username or password! Try again");
        return false;   //else false
    }

    public static void main(String[] args) {

        // Create a PasswordHasher object
        PasswordHasher passwordHasher = new PasswordHasher();

        // Create lists to store employee usernames and hashed passwords
        List<String> employee = new ArrayList<>();
        List<String> password = new ArrayList<>();

        // Add employee usernames and hashed passwords to the lists
        employee.add("Javier(STAFF)");
        employee.add("Darranda(STAFF)");
        employee.add("Emily(STAFF)");
        employee.add("Jacob(MANAGER)");
        // Hash the password before storing
        password.add(passwordHasher.hash("jav123"));
        password.add(passwordHasher.hash("dar123"));
        password.add(passwordHasher.hash("emi123"));
        password.add(passwordHasher.hash("jac123"));

        // Create a Scanner object for input
        Scanner scanner = new Scanner(System.in);

        // Create a UserService object with the employee and password lists
        UserService userService = new UserService(employee, password, scanner);

        System.out.println("\n*** WELCOME, PLEASE SIGN IN ***\n");

        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("What is your username?");
            String username = scanner.nextLine();
            System.out.println("What is your password?");
            String pw = scanner.nextLine();
            loggedIn = userService.login(username, pw);
        }

    }

}
