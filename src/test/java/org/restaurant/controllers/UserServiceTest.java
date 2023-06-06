package org.restaurant.controllers;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.restaurant.utils.PasswordHasher;

import java.util.ArrayList;
import java.util.List;

public class UserServiceTest {
    @DisplayName("Positive test with a valid username and password")
    @Test
    public void testLogin_Positive() {
        List<String> employee = new ArrayList<>();
        employee.add("John(STAFF)");
        employee.add("Jane(MANAGER)");

        List<String> password = new ArrayList<>();
        password.add(PasswordHasher.hash("john123"));
        password.add(PasswordHasher.hash("jane123"));

        UserService userService = new UserService(employee, password, null);

        boolean loggedIn = userService.login("John", "john123");
        Assertions.assertTrue(loggedIn);
    }

    @DisplayName("Negative test with invalid username")
    @Test
    public void testLogin_Negative() {
        List<String> employee = new ArrayList<>();
        employee.add("John(STAFF)");
        employee.add("Jane(MANAGER)");

        List<String> password = new ArrayList<>();
        password.add(PasswordHasher.hash("john123"));
        password.add(PasswordHasher.hash("jane123"));

        UserService userService = new UserService(employee, password, null);

        boolean loggedIn = userService.login("David", "john123");
        Assertions.assertFalse(loggedIn);
    }

    @DisplayName("Edge case test with empty username and password")
    @Test
    public void testLogin_EdgeCase() {
        List<String> employee = new ArrayList<>();
        employee.add("John(STAFF)");
        employee.add("Jane(MANAGER)");

        List<String> password = new ArrayList<>();
        password.add(PasswordHasher.hash("john123"));
        password.add(PasswordHasher.hash("jane123"));

        UserService userService = new UserService(employee, password, null);

        boolean loggedIn = userService.login("", "");
        Assertions.assertFalse(loggedIn);
    }

}