package com.company.test;

import com.company.restaurant.Database;
import com.company.users.Customer;
import com.company.users.User;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    String name = "test name";
    String email = "emailtest@email.com";
    Customer testUser = new Customer();
    private User user;
    @Test
    public void testEmail(){
        JSONObject userDetails = new JSONObject();
        userDetails.put("fullname", name);
        userDetails.put("email", email);
        userDetails.put("password", "password");
        userDetails.put("user_type", "customer");

        Database.writeToTable("user", userDetails);
        JSONObject user = Database.readFromUserTable(email, null);
        User.createUser(true, email);
        System.out.println( user.getString("email"));
        assertEquals(email, user.getString("email"));
        Database.deleteFromTable("user", "user_id", user.getInt("user_id"));
    }
}