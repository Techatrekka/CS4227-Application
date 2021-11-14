package com.company.ui;

import com.company.BusinessHours;
import com.company.Database;

import com.company.menu.Menu;
import com.company.users.*;
import org.json.JSONObject;

import java.util.*;

public class RestaurantTerminal extends UserInterface {
    BusinessHours businessHours = new BusinessHours();
    ArrayList<Menu> restaurantMenus = new ArrayList<>();

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

        while(!userLogin.isSuccessfulLogin()) {
            displayLoginScreen();
        }

        while(userLogin.isSuccessfulLogin()) {
            System.out.println("\nWelcome, " + user.getFullName() + ".");
            displayHomeScreen();
        }
    }

    private void displayHomeScreen() {
        businessHours.isOpenNow();
        System.out.println("\nEnter a number to choose what you'd like to do:");
        int choice;

        if(Objects.equals(user.getUserType(), "customer")) {
            Customer.addObservable((Customer) user, businessHours);
            System.out.println("You have " +  ((Customer) user).getLoyaltyPoints() + " loyalty points.");
            System.out.println("1. Place an order 2. View Menus 3. View Previous Orders 4. Settings 5. Logout 6. Quit");
            choice = getInput(1, 6);
            switch(choice) {
                case 1:
                    user.placeOrder(user.getIdNum());
                    break;
                case 2:
                    // @TODO: customer view menus - reuse method from staff??
                    break;
                case 3:
                    // @TODO: get previous orders for user from DB, user.getOrders()
                    break;
                case 4:
                    changePassword();
                    break;
                case 5:
                    logout();
                    break;
                case 6:
                    System.out.println("Shutting down system...");
                    System.exit(0);
            }
        } else if(Objects.equals(user.getUserType(), "employee")) {
            String employeeType = ((Staff) user).getEmployeeType();
            if(employeeType.equalsIgnoreCase("manager")) {
                System.out.println("1.Place Order 2. Menu Management 3. Employee Management 4. Stock Management 5. Settings 6. Logout 7. Quit");
                choice = getInput(1, 7);
                switch(choice) {
                    case 1:
                        System.out.println("Enter the user id of the user you'd like to place an order for");
                        // @TODO: get input. Check if valid id in DB
                        user.placeOrder(1);
                        break;
                    case 2:
                        menuManagement();
                        break;
                    case 3:
                        employeeManagement();
                        break;
                    case 4:
                        stockManagement();
                        break;
                    case 5:
                        changePassword();
                        break;
                    case 6:
                        logout();
                        break;
                    case 7:
                        System.out.println("Shutting down system...");
                        System.exit(0);
                }
            } else {
                // if clerk then
                System.out.println("1. Place Order 2. Stock Management 3. Logout 4. Quit");
                choice = getInput(1, 4);
                switch(choice) {
                    case 1:
                        System.out.println("Enter the user id of the user you'd like to place an order for");
                        // @TODO: get input. Check if valid id in DB
                        user.placeOrder(1);
                        break;
                    case 2:
                        stockManagement();
                        break;
                    case 3:
                        logout();
                        break;
                    case 4:
                        System.out.println("Shutting down system...");
                        System.exit(0);
                }
            }
        }
    }

    private void logout() {
        user = null;
        userLogin.setEmail("");
        userLogin.setSuccessfulLogin(false);
        userRegistration.setEmail("");
        displayLoginScreen();
    }

    private void stockManagement() {
        // @TODO: implement this
        System.out.println("1. ");
    }

    private void employeeManagement() {
        System.out.println("1. Add Employee 2. View Employees 3. Edit Employee 4. Remove Employee");
        int choice = getInput(1, 4);
        switch (choice) {
            // @TODO: implement all these methods inside Manager class
            case 1:
                ((Manager) user).addStaffMember();
                break;
            case 2:
                ((Manager) user).viewStaffMember();
                break;
            case 3:
                ((Manager) user).editStaffSalary();
                break;
            case 4:
                ((Manager) user).removeStaffMember();
                break;
        }
    }

    private void menuManagement() {
        System.out.println("1. Create Menu 2. Edit Menu 3. Delete Menu 4. View Menus");
        int choice = getInput(1, 4);
        switch(choice) {
            case 1:
                restaurantMenus.add(((Manager) user).makeMenu());
                break;
                // @TODO: implement next 2 methods in manager class
            case 2:
                // read menus from database and ask which to edit
                ((Manager) user).editMenu();

                // System.out.println("What type of menu item would you like to create? 1. Beverage 2. Dish");
                // choice = getInput(1, 2);
              //  menu.addNewMenuItem(choice);
                break;
            case 3:
                // read menus from database and ask which to delete
                ((Manager) user).deleteMenu();
              //  restaurantMenus.remove(menu)
                break;
            case 4:
                user.viewMenu();
                // for(Menu menu : restaurantMenus) {
                //     System.out.println(menu);
                // }
                break;
        }
    }

    private void changePassword() {
        System.out.println("Do you want to change your password? 0 = No, 1 = Yes");
        int choice = getInput(0,1);
        if(choice == 1) {
            String newPass = getNewPassword();
            JSONObject userNewPass = new JSONObject();
            userNewPass.put("password", newPass);
            userNewPass.put("user_id", user.getIdNum());
            System.out.println(userNewPass);
            if(Database.updateTable("user", userNewPass)) {
                    System.out.println("Password changed successfully");
            } else {
                System.out.println("Sorry, password was not changed.");
            }
        }
    }

    private void displayLoginScreen() {
        System.out.println("Enter a number to choose what you'd like to do");
        System.out.println("1. Login 2. Register 3. Quit");
        int numChoice = getInput(1, 3);

        switch (numChoice) {
            case 1:
                userLogin.displayLoginPrompt();
                if(userLogin.isSuccessfulLogin()) {
                    user = User.createUser(false, userLogin.getEmail());
                }
                break;
            case 2:
                boolean success = userRegistration.registerNewUser("customer");
                userLogin.setSuccessfulLogin(success);
                if(success) {
                    user = User.createUser(true, userRegistration.getNewUserEmail());
                }
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
