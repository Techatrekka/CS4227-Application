package com.company.users;
import org.json.JSONObject;

import java.util.Objects;

public class UserFactory {
    public UserFactory() {}

    public User createUser(JSONObject user) {
        if(Objects.equals(user.getString("userType"), "customer")) {
            return new Customer(user.getInt("userID"), user.getString("email"),
                    user.getString("fullName"), user.getString("password"), user.getInt("loyaltyPoints"));
        } else if(Objects.equals(user.getString("userType"), "staff")) {
            return new Staff(user.getInt("userID"), user.getString("email"),
                    user.getString("fullName"), user.getString("employeeType"), user.getDouble("salary"));
        } else {
            return null;
        }
    }
}
