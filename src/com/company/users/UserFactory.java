package com.company.users;
import org.json.JSONObject;

import java.util.Objects;

public class UserFactory {
    public UserFactory() { // empty default constructor
    }

    public User createUser(JSONObject user) {
        String userId = "user_id";
        String email = "email";
        String employeeType = "employee_type";
        String fullName = "fullname";
        if(Objects.equals(user.getString("user_type"), "customer")) {
            return new Customer(user.getInt(userId), user.getString(email),
                    user.getString(fullName), user.getInt("loyalty_points"));
        } else if(Objects.equals(user.getString("user_type"), "employee")) {
            if(Objects.equals(user.getString(employeeType), "manager")) {
                return new Manager(user.getInt(userId), user.getString(email),
                        user.getString(fullName), user.getString(employeeType), user.getDouble("salary"));
            } else {
                return new Staff(user.getInt(userId), user.getString(email),
                        user.getString(fullName), user.getString(employeeType), user.getDouble("salary"));
            }
        } else {
            return null;
        }
    }
}
