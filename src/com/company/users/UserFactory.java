package com.company.users;
import org.json.JSONObject;

import java.util.Objects;

public class UserFactory {
    public UserFactory() {}

    public User createUser(JSONObject user) {
        if(Objects.equals(user.getString("user_type"), "customer")) {
            return new Customer(user.getInt("user_id"), user.getString("email"),
                    user.getString("fullname"), user.getInt("loyalty_points"));
        } else if(Objects.equals(user.getString("user_type"), "employee")) {
            if(Objects.equals(user.getString("employee_type"), "manager")) {
                return new Manager(user.getInt("user_id"), user.getString("email"),
                        user.getString("fullname"), user.getString("employee_type"), user.getDouble("salary"));
            } else {
                return new Staff(user.getInt("user_id"), user.getString("email"),
                        user.getString("fullname"), user.getString("employee_type"), user.getDouble("salary"));
            }
        } else {
            return null;
        }
    }
}
