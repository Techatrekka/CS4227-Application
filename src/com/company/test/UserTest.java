package com.company.test;

import com.company.restaurant.Database;
import com.company.users.Customer;
import com.company.users.User;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

class UserTest {
    String name = "test name";
    String email = "emailtest@email.com";
    Customer testUser = new Customer();
    @Test
    public void testEmail(){
        String emailKey = "email";
        JSONObject userDetails = new JSONObject();
        userDetails.put("fullname", name);
        userDetails.put(emailKey, email);
        userDetails.put("password", "password");
        userDetails.put("user_type", "customer");

        Database.writeToTable("user", userDetails);
        JSONObject user = Database.readFromUserTable(email, null);
        User.createUser(true, email);
        System.out.println( user.getString(emailKey));
        assertEquals(email, user.getString(emailKey));
        Database.deleteFromTable("user", "user_id", user.getInt("user_id"));
    }
}