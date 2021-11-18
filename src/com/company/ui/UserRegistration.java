package com.company.ui;

import com.company.Database;
import org.json.JSONObject;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Objects;
import java.util.Scanner;

public class UserRegistration extends UserInterface {
    Scanner scanner = new Scanner(System.in);
    private String newUserEmail = "";

    String getNewUserEmail() {
        return this.newUserEmail;
    }

    public boolean registerNewUser(String userType) {
        System.out.println("Enter Q on its own in the email or password field to shut down the system, or enter B to go back to the previous screen.");
        String email1 = getEmail();

        JSONObject existingUser = Database.readFromUserTable(email1, null);
        if(existingUser.has("email") && Objects.equals(existingUser.getString("email"), email1)) {
            System.out.println("Sorry, that email has already been used to register an account. Please use a different one or login if this is your account.");
            email1 = getEmail();
        }

        String email = email1.replace(" ", "");

        if(Objects.equals(email, "false")) return false;
        System.out.println("Enter your full name:");
        String name = scanner.nextLine();

        JSONObject userDetails = new JSONObject();
        userDetails.put("fullname", name);
        userDetails.put("email", email);
        /*
        //Ansible - can't use on Windows OS
        try {
            FileWriter f2 = new FileWriter(System.getProperty("user.dir") + "\\src\\com\\company\\ansible\\accountInfo.json", false);
            f2.write(userDetails.toString());
            f2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Execute Ansible script here to generate password
         System.out.println(System.getProperty("user.dir") + "\\src\\com\\company\\ansible\\emailPassword.yml");
        String ansible_run = "ansible-playbook \"" + System.getProperty("user.dir") + "\\src\\com\\company\\ansible\\emailPassword.yml\"" ;
        try {
            System.out.println(ansible_run);
            Process p = Runtime.getRuntime().exec(ansible_run);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("You've been sent an email containing a secure password. Use it to login now.");
         */

        String password = getNewPassword();
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

    private String getEmail() {
        System.out.println("Enter the email address you want to register with:");
        String email = scanner.nextLine();
        checkQ(email);
        if(inputB(email)) {
            return "false";
        }

        while(!isValidEmailAddress(email)) {
            System.out.println("Please enter a valid email address");
            email = scanner.nextLine();
            checkQ(email);
            if(inputB(email)) {
                return "false";
            }
        }
        //check for duplicate - can't register if so
        return email;
    }

    private boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
