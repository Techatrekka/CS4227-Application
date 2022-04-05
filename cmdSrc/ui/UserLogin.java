package ui;

import restaurant.Database;
import org.json.JSONObject;

import java.util.Scanner;

public class UserLogin {
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
        System.out.println("Enter Email Address:");
        String email = scanner.nextLine();
        UiUtils.checkQ(email);
        if(UiUtils.inputB(email)) {
            return;
        }
        String password = UiUtils.getPassword("Enter ");

        JSONObject userDetails = Database.readFromUserTable(email,password);
        boolean validCredentials = userDetails.has("correct_pass");
        while(!validCredentials) {
            System.out.println("Please enter valid login details. You must register before you can login.");
            System.out.println("Enter Email address:");
            email = scanner.nextLine();
            if(UiUtils.inputB(email)) {
                this.setSuccessfulLogin(false);
                return;
            }
            password = UiUtils.getPassword("Enter ");
            userDetails = Database.readFromUserTable(email,password);
            validCredentials = userDetails.has("correct_pass");
        }
        this.setSuccessfulLogin(true);
        this.email = email;
    }
    protected void setEmail(String email) {
        this.email = email;
    }
}
