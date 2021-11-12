package com.company.ui;

import com.company.BusinessHours;
import com.company.Database;
import com.company.users.Customer;
import com.company.users.User;
import com.company.users.UserFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class RestaurantTerminal {
    BusinessHours businessHours = new BusinessHours();

    private Scanner scanner = new Scanner(System.in);
    private UserLogin userLogin;
    private UserRegistration userRegistration;
    private User user;

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
        System.out.println(businessHours.toString());

        userLogin = new UserLogin();
        userRegistration = new UserRegistration();

        displayHomeScreen();

        while(!userLogin.isSuccessfulLogin()) {
            displayHomeScreen();
        }

        createUser();

        System.out.println("\nWelcome, " + user.getFullName() + ". You have " + "loyalty points.");
        businessHours.isOpenNow();

        System.out.println("\nEnter a number to choose what you'd like to do:");
        // if customer then this - need var for user type
        if(Objects.equals(user.getUserType(), "customer")) {
            System.out.println("1. Place an order 2. View Menus 3. View Previous Orders 4. Settings 5. Logout 6. Quit");

        } else if(Objects.equals(user.getUserType(), "employee")) {
            // if clerk then
            // if manager then can add/remove employees - this includes account setup, they can't register themselves
        }

    }

    private void createUser() {
        UserFactory userFactory = new UserFactory();
        JSONObject existingUser = Database.readFromUserTable(userLogin.getEmail(), null);
        List<String> cols = new ArrayList<>();
        if(Objects.equals(existingUser.getString("userType"), "employee")) {
            cols.add("salary");
            cols.add("employee_Type");
            JSONObject employeeTypeSalary = Database.readFromTable("employeesalary", existingUser.getInt("userID"), cols);
            existingUser.put("salary", employeeTypeSalary.getDouble("salary"));
            existingUser.put("employee_type", employeeTypeSalary.getString("employee_type"));
        } else {
            cols.add("loyalty_points");
            JSONObject userLoyalty = Database.readFromTable("loyalty", existingUser.getInt("userID"), cols);
            existingUser.put("loyaltyPoints", userLoyalty.getInt("loyalty_points"));
        }

        user = userFactory.createUser(existingUser);
        Customer.addObservable((Customer) user, businessHours);
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
                if(success) changePassword();
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
