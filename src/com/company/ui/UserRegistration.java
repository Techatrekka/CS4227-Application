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

    public boolean registerNewUser() {
        System.out.println("Enter Q on its own in the email or password field to shut down the system, or enter B to go back to the previous screen.");
        String email = getEmail();
        if(Objects.equals(email, "false")) return false;
        System.out.println("Enter your full name:");
        String name = scanner.nextLine();

        JSONObject userDetails = new JSONObject();
        userDetails.put("fullname", name);
        userDetails.put("email", email);
        /*
        //Ansible attempt
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

        String password = getPassword();
        if(Objects.equals(password, "false")) return false;
        userDetails.put("password", password);
        userDetails.put("user_type", "customer");

        Database.writeToDatabase("user", userDetails);

        System.out.println("Congratulations, you've successfully registered!");
        this.newUserEmail = email;
        return true;
    }

    private String getPassword() {
        System.out.println("Password:");
        String password = scanner.nextLine();
        checkQ(password);
        if(inputB(password)) {
            return "false";
        }
        password = checkPasswordLength(password);
        System.out.println("Repeat password:");
        String repeatPass = scanner.nextLine();
        while(!Objects.equals(repeatPass, password)) {
            System.out.println("Please enter the same password twice.\nPassword:");
            password = scanner.nextLine();
            checkQ(password);
            if(inputB(password)) {
                return "false";
            }
            password = checkPasswordLength(password);
            System.out.println("Repeat password:");
            repeatPass = scanner.nextLine();
        }
        return password;
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

    private String checkPasswordLength(String password) {
        while(password.length() < 8) {
            System.out.println("Password must be at least 8 characters, please try again");
            password = scanner.nextLine();
        }
        return password;
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
