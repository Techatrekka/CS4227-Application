package com.company.users;
import org.json.JSONObject;

import java.util.Objects;

public class UserFactory {
    public UserFactory() {}

    public User createUser(JSONObject user) {
        if(Objects.equals(user.getString("userType"), "customer")) {
            return new Customer(user.getInt("userID"), user.getString("email"),
                    user.getString("fullName"), user.getInt("loyalty_points"));
        } else if(Objects.equals(user.getString("userType"), "employee")) {
            if(Objects.equals(user.getString("employee_type"), "manager")) {
                return new Manager(user.getInt("userID"), user.getString("email"),
                        user.getString("fullName"), user.getString("employee_type"), user.getDouble("salary"));
            } else {
                return new Staff(user.getInt("userID"), user.getString("email"),
                        user.getString("fullName"), user.getString("employee_type"), user.getDouble("salary"));
            }
        } else {
            return null;
        }
    }
}
