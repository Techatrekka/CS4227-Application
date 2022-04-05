package com.company.ui;

import com.company.restaurant.Database;
import org.json.JSONObject;

import java.util.Objects;
import java.util.Scanner;

public class UserRegistration {
    private Scanner scanner = new Scanner(System.in);
    private String newUserEmail = "";

    String getNewUserEmail() {
        return this.newUserEmail;
    }

    public boolean registerNewUser(String userType) {
        System.out.println("Enter Q on its own in the email or password field to shut down the system, or enter B to go back to the previous screen.");
        String email1 = UiUtils.getEmail();

        String email = email1.replace(" ", "");

        if(Objects.equals(email, "false")) return false;
        System.out.println("Enter your full name:");
        String name = scanner.nextLine();

        JSONObject userDetails = new JSONObject();
        userDetails.put("fullname", name);
        userDetails.put("email", email);

        String password = UiUtils.getNewPassword();
        if(Objects.equals(password, "false")) return false;
        userDetails.put("password", password);
        userDetails.put("user_type", userType);

        Database.writeToTable("user", userDetails);

        System.out.println("Congratulations, you've successfully registered!");

        this.newUserEmail = email;
        return true;
    }

    protected void setEmail(String email) {
        this.newUserEmail = email;
    }
}
