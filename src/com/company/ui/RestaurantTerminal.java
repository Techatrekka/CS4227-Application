package com.company.ui;

import com.company.BusinessHours;
import com.company.users.UserFactory;
import java.util.*;

public class RestaurantTerminal {
    private Scanner scanner = new Scanner(System.in);
    private UserLogin userLogin;
    private UserRegistration userRegistration;

    private static RestaurantTerminal single_instance = null;

    private RestaurantTerminal() {
        System.out.println("Welcome to JJ's Diner!");
    }

    public static RestaurantTerminal getInstance()
    {
        if (single_instance == null)
            single_instance = new RestaurantTerminal();

        return single_instance;
    }

    public void run() {
        BusinessHours businessHours = new BusinessHours();
        System.out.println(businessHours.toString());

        userLogin = new UserLogin();
        userRegistration = new UserRegistration();

        displayHomeScreen();

        while(!userLogin.isSuccessfulLogin()) {
            displayHomeScreen();
        }
        // TODO: Don't actually need to have password field in Java - remove from all constructors
        // TODO: Instantiate objects for the logged in/registered user - store email in UI instance as LoggedInUserEmail ? Then create obj in this class?
        // TODO: Read and write from database
        System.out.println("Successful login. Enter a number to choose what you'd like to do:");
        // if customer then this - need var for user type
        System.out.println("1. Place an order 2. View Menus 3. Settings 4. Logout 5. Quit");
        // if clerk then

        // if manager then can add/remove emmployees - this includes account setup, they can't register themselves

        UserFactory userFactory = new UserFactory();
     /*   JSONObject managerDetails = new JSONObject(
                "{\"userID\":\"1\",\"userType\":\"staff\",\"fullName\":\"John doe\",\"email\":\"johndoe@gmail.com\", \"employeeType\":\"Manager\", \"salary\":\"20000\"}"
        );
        User manager = userFactory.createUser(managerDetails);
        JSONObject customerDetails = new JSONObject(
                "{\"userID\":\"2\",\"userType\":\"customer\",\"fullName\":\"Jane doe\",\"email\":\"janedoe@gmail.com\", \"password\":\"blahblah\", \"loyaltyPoints\":\"0\"}"
        );
        User customer = userFactory.createUser(customerDetails);
        JSONObject customer2Details = new JSONObject(
                "{\"userID\":\"3\",\"userType\":\"customer\",\"fullName\":\"Jill doe\",\"email\":\"jilldoe@gmail.com\", \"password\":\"blahblah\", \"loyaltyPoints\":\"10\"}"
        );
        User customer2 = userFactory.createUser(customer2Details);

        System.out.println(manager);
        System.out.println(customer);
        System.out.println(customer2);

        //Don't ask for this, just display it for all customers when they log in.
        System.out.println("\nDoes customer1 want opening hours notifications? 0 = No, 1 = Yes");
        numChoice = getInput(0, 1);
        if(numChoice == 1) {
            Customer.addObservable((Customer) customer, businessHours);
        }
        System.out.println("\nDoes customer2 want opening hours notifications? 0 = No, 1 = Yes");
        numChoice = getInput(0, 1);
        if(numChoice == 1) {
            Customer.addObservable((Customer) customer2, businessHours);
        }
*/
        if(!businessHours.isOpenNow()) {
            System.out.println("Sorry, the restaurant is closed right now - you won't be able to place any orders.");
        } else {
            System.out.println("The restaurant is currently open.");
        }
    }

    private void changePassword() {
        System.out.println("Do you want to change your password? 0 = No, 1 = Yes");
        int choice = getInput(0,1);
        if(choice == 1) {
            // change password here
        }
    }

    private void displayHomeScreen() {
        System.out.println("Enter a number to choose what you'd like to do");
        System.out.println("1. Login 2. Register 3. Quit");
        int numChoice = getInput(1, 3);

        switch (numChoice) {
            case 1:
                userLogin.displayLoginPrompt();
                break;
            case 2:
                boolean success = userRegistration.registerNewUser();
                userLogin.setSuccessfulLogin(success);
                changePassword();
                break;
            case 3:
                System.out.println("Shutting down system.");
                System.exit(0);
                break;
        }
    }

    private int getInput(int min, int max) {
        String choice = scanner.nextLine();

        while(!isValid(choice, min, max)) {
            System.out.println("Please enter a valid number.");
            choice = scanner.nextLine();
        }
        return Integer.parseInt(choice);
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
