package org.restaurant.models;

public class User {
    private String username;
    private String password;
    private Role role;

    public enum Role {
        STAFF, MANAGER
    }

    //come back to delete getters and setters if not being used - check Wednesday afternoon!
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
