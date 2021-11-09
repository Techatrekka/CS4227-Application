package com.company;

import com.company.users.User;
import com.company.users.UserFactory;
import org.json.JSONObject;
import java.util.*;

public class RestaurantTerminal {
    private Scanner scanner = new Scanner(System.in);

    private static RestaurantTerminal single_instance = null;
    BusinessHours businessHours;

    private RestaurantTerminal() {
        businessHours = new BusinessHours();
        System.out.println("Welcome to JJ's Diner!");
    }

    public static RestaurantTerminal getInstance()
    {
        if (single_instance == null)
            single_instance = new RestaurantTerminal();

        return single_instance;
    }

    void run() {
        System.out.println(businessHours.toString());
        if(!businessHours.isOpenNow()) {
            System.out.println("Sorry, the restaurant is closed right now");
        }

        System.out.println("Enter a number to choose what you'd like to do");
        System.out.println("1. Login 2. Register 3. Quit");
        String choice = scanner.nextLine();

        while(!isValid(choice, 1, 3)) {
            System.out.println("Please enter a valid number.");
            choice = scanner.nextLine();
        }
        int numChoice = Integer.parseInt(choice);

        switch (numChoice) {
            case 1:
                System.out.println("Logging in");
                break;
            case 2:
                System.out.println("Registering");
                break;
            case 3:
                System.out.println("Shutting down system.");
                System.exit(0);
                break;
        }

        UserFactory userFactory = new UserFactory();
        JSONObject managerDetails = new JSONObject(
                "{\"userID\":\"1\",\"userType\":\"staff\",\"fullName\":\"John doe\",\"email\":\"johndoe@gmail.com\", \"employeeType\":\"Manager\", \"salary\":\"20000\"}"
        );
        User manager = userFactory.createUser(managerDetails);
        System.out.println(manager);
    }

    private boolean isValid(String choice, int min, int max) {
        try {
            int numChoice = Integer.parseInt(choice);
            if(numChoice > max || numChoice < min) {
                return false;
            }
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}
