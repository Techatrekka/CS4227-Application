package com.company.ui;

import com.company.Database;
import org.json.JSONObject;
import java.util.Scanner;

public class UserLogin extends UserInterface {
    private boolean successfulLogin = false;
    private String email = "";

    public UserLogin() {
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
        System.out.println("Enter Email address:");
        String email = scanner.nextLine();
        checkQ(email);
        if(inputB(email)) {
            return;
        }
        System.out.println("Enter Password:");
        String password = scanner.nextLine();
        JSONObject userDetails = Database.readFromUserTable(email,password);
        boolean validCredentials = userDetails.has("password");
        while(!validCredentials) {
            System.out.println("Please enter valid login details. You must register before you can login.");
            System.out.println("Enter Email address:");
            email = scanner.nextLine();
            if(inputB(email)) {
                this.setSuccessfulLogin(false);
                return;
            }
            System.out.println("Enter Password:");
            password = scanner.nextLine();
            userDetails = Database.readFromUserTable(email,password);
            validCredentials = userDetails.has("password");
        }
        this.setSuccessfulLogin(true);
        this.email = email;
    }
}
