package com.company.ui;

import java.io.Console;
import java.util.Objects;
import java.util.Scanner;

public abstract class UserInterface {
    private Scanner scanner = new Scanner(System.in);
    
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
        String password = getPassword("Enter ");
        checkQ(password);
        if(inputB(password)) {
            return "false";
        }
        password = checkPasswordLength(password);
        String repeatPass = getPassword("Repeat ");
        while(!Objects.equals(repeatPass, password)) {
            password = getPassword("Please enter the same password twice.\n");
            checkQ(password);
            if(inputB(password)) {
                return "false";
            }
            password = checkPasswordLength(password);
            repeatPass = getPassword("Repeat ");
        }
        return password;
    }

    String getPassword(String repeat) {
        String password;
        Console console = System.console();
        if(console != null) {
            password = new String(console.readPassword(repeat + "Password: "));
        } else {
            System.out.println(repeat + "Password: ");
            password = scanner.nextLine();
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
