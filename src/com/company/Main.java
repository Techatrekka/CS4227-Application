package com.company;

import com.company.users.User;
import com.company.users.UserFactory;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        UserFactory userFactory = new UserFactory();
        JSONObject managerDetails = new JSONObject(
                "{\"userID\":\"1\",\"userType\":\"staff\",\"fullName\":\"John doe\",\"email\":\"johndoe@gmail.com\", \"employeeType\":\"Manager\", \"salary\":\"20000\"}"
        );
        User manager = userFactory.createUser(managerDetails);
        System.out.println(manager);
    }
}
