package com.company.ui;

import java.util.Objects;
import java.util.Scanner;

public abstract class UserInterface {
    Scanner scanner = new Scanner(System.in);
    boolean inputB(String input) {
        if(input.equalsIgnoreCase("b")) {
            System.out.println("Returning to previous screen..");
            return true;
        }
        return false;
    }

    void checkQ(String input) {
        if(input.equalsIgnoreCase("q")) {
            System.out.println("Shutting down system...");
            System.exit(0);
        }
    }

    String getNewPassword() {
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

    String checkPasswordLength(String password) {
        while(password.length() < 8) {
            System.out.println("Password must be at least 8 characters, please try again");
            password = scanner.nextLine();
            if(inputB(password)) {
                return "false";
            }
        }
        return password;
    }
}
