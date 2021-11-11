package com.company.ui;

import com.company.ui.UserInterface;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Objects;
import java.util.Scanner;

public class UserRegistration extends UserInterface {
    Scanner scanner = new Scanner(System.in);

    public boolean registerNewUser() {
        System.out.println("Enter Q on its own in the email or password field to shut down the system, or enter B to go back to the previous screen.");
        String email = getEmail();
        if(Objects.equals(email, "false")) return false;

        System.out.println("Enter your full name:");
        String name = scanner.nextLine();
        // Execute Ansible script here to generate password
       // ProcessBuilder processBuilder = new ProcessBuilder("ansible-playbook", "../ansible/emailPassword.yml");
        String password = getPassword();
        if(Objects.equals(password, "false")) return false;

        // create user in DB here - need email, password, full name for this. Add user type
        System.out.println("Congratulations, you've successfully registered!");
        return true;
    }

    private String getPassword() {
        System.out.println("You've been sent an email containing a secure password. Use it to login now.");
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
