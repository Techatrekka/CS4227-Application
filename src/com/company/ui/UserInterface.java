package com.company.ui;

public abstract class UserInterface {
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
}
