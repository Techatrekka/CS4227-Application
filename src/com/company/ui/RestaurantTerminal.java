package com.company.ui;

import com.company.BusinessHours;
import com.company.Database;
import com.company.users.Customer;
import com.company.users.Staff;
import com.company.users.User;
import com.company.users.UserFactory;
import org.json.JSONObject;

import java.util.*;

public class RestaurantTerminal extends UserInterface {
    BusinessHours businessHours = new BusinessHours();

    private Scanner scanner = new Scanner(System.in);
    private UserLogin userLogin;
    private UserRegistration userRegistration;
    private User user;
    private JSONObject currentUser;

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

        System.out.println("\nWelcome, " + user.getFullName() + ".");
        businessHours.isOpenNow();

        System.out.println("\nEnter a number to choose what you'd like to do:");

        // if customer then this - need var for user type
        if(Objects.equals(user.getUserType(), "customer")) {
            System.out.println("You have " +  ((Customer) user).getLoyaltyPoints() + " loyalty points.");
            System.out.println("1. Place an order 2. View Menus 3. View Previous Orders 4. Settings 5. Logout 6. Quit");
            String choice = scanner.nextLine();
            if(Objects.equals(choice, "1")) changePassword();
        } else if(Objects.equals(user.getUserType(), "employee")) {
            String employeeType = ((Staff) user).getEmployeeType();
            if(employeeType.equalsIgnoreCase("manager")) {
                // if manager then can add/remove employees - this includes account setup, they can't register themselves
            } else {
                // if clerk then
            }
        }
    }

    private void createUser() {
        UserFactory userFactory = new UserFactory();
        System.out.println("user reg email is " + userRegistration.getNewUserEmail());
        System.out.println("user login email is " + userLogin.getEmail());
        List<String> cols = new ArrayList<>();
        JSONObject userLoyalty = new JSONObject();

        if(Objects.equals(userLogin.getEmail(), "") ) {
            System.out.println("user login is empty");
            currentUser = Database.readFromUserTable(userRegistration.getNewUserEmail(), null);
            if(Objects.equals(currentUser.getString("userType"), "customer")) {
                userLoyalty.put("user_id", currentUser.getInt("userID"));
                userLoyalty.put("loyalty_points", 0);
                if(Database.writeToDatabase("loyalty", userLoyalty)) {
                    System.out.println("created in loyalty table");
                } else {
                    System.out.println("done fucked");
                }
                currentUser.put("loyalty_points", 0);
            }
        } else {
            System.out.println("user reg is empty");
            currentUser = Database.readFromUserTable(userLogin.getEmail(), null);
            if(Objects.equals(currentUser.getString("userType"), "customer")) {
                cols.add("loyalty_points");
                userLoyalty = Database.readFromTable("loyalty", currentUser.getInt("userID"), cols);
                System.out.println(userLoyalty);
                currentUser.put("loyalty_points", userLoyalty.getInt("loyalty_points"));
            }
        }

        System.out.println(currentUser);
        if(Objects.equals(currentUser.getString("userType"), "employee")) {
            cols.add("salary");
            cols.add("employee_Type");
            JSONObject employeeTypeSalary = Database.readFromTable("employeesalary", currentUser.getInt("userID"), cols);
            currentUser.put("salary", employeeTypeSalary.getDouble("salary"));
            currentUser.put("employee_type", employeeTypeSalary.getString("employee_type"));
        }
        user = userFactory.createUser(currentUser);
        Customer.addObservable((Customer) user, businessHours);
    }

    private void changePassword() {
        System.out.println("Do you want to change your password? 0 = No, 1 = Yes");
        int choice = getInput(0,1);
        if(choice == 1) {
            // change password here
            String newPass = getNewPassword();
            System.out.println(currentUser);
            currentUser.put("password", newPass);
            System.out.println(currentUser);
            if(Database.deleteFromTable("user", "user_id", currentUser.getInt("userID"))) {
                JSONObject userNewPass = currentUser;
                userNewPass.remove("loyalty_points");
                if(Database.writeToDatabase("user", userNewPass)) {
                    System.out.println("Password changed successfully");
                } else {
                    System.out.println("Sorry, password was not changed.");
                }
            } else {
                System.out.println("Sorry, password was not changed.");
            }

        }
    }

    private void displayHomeScreen() {
        System.out.println("Enter a number to choose what you'd like to do");
        System.out.println("1. Login 2. Register 3. Quit");
        int numChoice = getInput(1, 3);

        switch (numChoice) {
            case 1:
                userLogin.displayLoginPrompt();
                if(userLogin.isSuccessfulLogin()) createUser();
                break;
            case 2:
                boolean success = userRegistration.registerNewUser();
                userLogin.setSuccessfulLogin(success);
                if(success) {
                    createUser();
                    changePassword();
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
