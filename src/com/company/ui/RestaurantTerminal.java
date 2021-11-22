package com.company.ui;

import com.company.restaurant.BusinessHours;
import com.company.restaurant.Database;
import com.company.restaurant.Restaurant;
import com.company.menu.Menu;
import com.company.users.*;
import org.json.JSONObject;

import java.util.*;

public class RestaurantTerminal extends UserInterface {
    BusinessHours businessHours = new BusinessHours();
    ArrayList<Menu> restaurantMenus;

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
        Restaurant res = new Restaurant();
        restaurantMenus = res.initMenus();
        System.out.println(businessHours.toString());

        userLogin = new UserLogin();
        userRegistration = new UserRegistration();

        while(!userLogin.isSuccessfulLogin()) {
            displayLoginScreen();
        }
        System.out.println("\nWelcome, " + user.getFullName() + ".");
        while(userLogin.isSuccessfulLogin()) {
            displayHomeScreen();
        }
    }

    private void displayHomeScreen() {
        System.out.println("\nEnter a number to choose what you'd like to do:");
        int choice;

        if(Objects.equals(user.getUserType(), "customer")) {
            System.out.println("You have " +  ((Customer) user).getLoyaltyPoints() + " loyalty points.");
            System.out.println("1. Place an order 2. View Menus 3. View Previous Orders 4. Settings 5. Logout 6. Quit");
            choice = getInput(1, 6);
            switch(choice) {
                case 1:
                    if(businessHours.isOpenNow()) {
                        user.placeOrder(user.getIdNum(), restaurantMenus);
                    } else {
                       break;
                    }
                    break;
                case 2:
                    user.viewMenu(restaurantMenus, "view:");
                    break;
                case 3:
                    user.getOrders();
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
                default:
                    break;
            }
        } else if(Objects.equals(user.getUserType(), "employee")) {
            String employeeType = ((Staff) user).getEmployeeType();
            if(employeeType.equalsIgnoreCase("manager")) {
                System.out.println("1.Place Order 2. Menu Management 3. Employee Management 4. Stock Management 5. Settings 6. Logout 7. Quit");
                choice = getInput(1, 7);
                switch(choice) {
                    case 1:
                        staffPlaceOrder();
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
                    default:
                        break;
                }
            } else {
                // if clerk then
                System.out.println("1. Place Order 2. Stock Management 3. Logout 4. Quit");
                choice = getInput(1, 4);
                switch(choice) {
                    case 1:
                        staffPlaceOrder();
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
                    default:
                        break;
                }
            }
        }
    }

    private void staffPlaceOrder() {
        System.out.println("Enter the user id of the user you'd like to place an order for");
        System.out.println("B = go back");
        String idChoice = scanner.nextLine();
        if(inputB(idChoice)) return;
        int id = Integer.parseInt(idChoice);
        if(businessHours.isOpenNow()) {
            user.placeOrder(id, restaurantMenus);
        } else {
            System.out.println("Sorry, you can't place an order right now as the restaurant is closed.");
        }
    }

    private void logout() {
        if(user instanceof Customer) businessHours.removeObserver((Customer)user);
        user = null;
        userLogin.setEmail("");
        userLogin.setSuccessfulLogin(false);
        userRegistration.setEmail("");
        displayLoginScreen();
    }

    private void stockManagement() {
        System.out.println("1. View stock 2. Order stock");
        System.out.println("Sorry, this use case was not implemented");
    }

    private void employeeManagement() {
        System.out.println("1. Add Employee 2. View Employees 3. Edit Employee 4. Remove Employee\nB = go back");
        int choice = getInput(1, 4);
        switch (choice) {
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
        System.out.println("NB: You can add/remove menu items from the edit menu section. B = go back.");
        int choice = getInput(1, 4);
        switch(choice) {
            case 1:
                restaurantMenus.add(((Manager) user).makeMenu());
                break;
            case 2:
                int menuID = user.viewMenu(restaurantMenus, "edit:");
                for(Menu menu : restaurantMenus) {
                    if(menu.getId() == menuID) {
                        ((Manager) user).editMenu(menu);
                    }
                }
                break;
            case 3:
                menuID = user.viewMenu(restaurantMenus, "delete:");
                ((Manager) user).deleteMenu(menuID);
                restaurantMenus.removeIf(menu -> menu.getId() == menuID);
                break;
            case 4:
                user.viewMenu(restaurantMenus, "view:");
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
                    if(user instanceof Customer) Customer.addObservable((Customer) user, businessHours);
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
        if(inputB(choice)) return -1;
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
