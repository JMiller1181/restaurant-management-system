package org.restaurant;

import org.restaurant.controllers.UserService;
import org.restaurant.models.User;
import org.restaurant.utils.PasswordHasher;

import java.util.Scanner;

import static org.restaurant.controllers.UserService.*;

public class Main {
    public static void main(String[] args) {
        // Create a PasswordHasher object
        PasswordHasher passwordHasher = new PasswordHasher();

        // Create a Scanner object for input
        Scanner scanner = new Scanner(System.in);

        // Create a UserService object with the employee and password lists
        UserService userService = new UserService();

        //login method
        User.Role userRole = loginScreen(userService, scanner);

        while (userRole != null) {
            if (userRole == User.Role.MANAGER) {
                boolean exitSession = handleManagerMenu(scanner, userService);
                if (exitSession) {
                    break;
                }
            } else if (userRole == User.Role.STAFF) {
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
