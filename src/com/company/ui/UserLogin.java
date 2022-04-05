package com.company.ui;

import com.company.restaurant.Database;
import org.json.JSONObject;

import java.util.Scanner;

public class UserLogin {
    private boolean successfulLogin = false;
    private String email = "";

    public UserLogin() {
        // Empty default constructor
    }

    protected String getEmail() {
        return this.email;
    }

    protected void setSuccessfulLogin(boolean success) {
        successfulLogin = success;
    }

    protected boolean isSuccessfulLogin() {
        return this.successfulLogin;
    }

    protected void displayLoginPrompt() {
        System.out.println("Enter Q on its own in the email field to shut down the system, or enter B to go back to the previous screen.");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Email Address:");
        String emailInput = scanner.nextLine();
        UiUtils.checkQ(emailInput);
        if(UiUtils.inputB(emailInput)) {
            return;
        }
        String password = UiUtils.getPassword("Enter ");

        JSONObject userDetails = Database.readFromUserTable(emailInput,password);
        boolean validCredentials = userDetails.has("correct_pass");
        while(!validCredentials) {
            System.out.println("Please enter valid login details. You must register before you can login.");
            System.out.println("Enter Email address:");
            emailInput = scanner.nextLine();
            if(UiUtils.inputB(emailInput)) {
                this.setSuccessfulLogin(false);
                return;
            }
            password = UiUtils.getPassword("Enter ");
            userDetails = Database.readFromUserTable(emailInput,password);
            validCredentials = userDetails.has("correct_pass");
        }
        this.setSuccessfulLogin(true);
        this.email = emailInput;
    }
    protected void setEmail(String email) {
        this.email = email;
    }
}
