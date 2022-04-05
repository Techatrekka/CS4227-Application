package ui;

import restaurant.BusinessHours;
import restaurant.Database;
import restaurant.RestaurantInit;
import menu.Menu;
import stock.Stock;
import stock.StockComponent;
import users.*;
import org.json.JSONObject;

import java.util.*;

public class RestaurantTerminal {
    private ArrayList<Menu> restaurantMenus;
    private StockComponent stock;
    private UserLogin userLogin;
    private UserRegistration userRegistration;
    private User user;
    private int stockCapacity = 15000;

    private BusinessHours businessHours = new BusinessHours();
    private Scanner scanner = new Scanner(System.in);
    private static RestaurantTerminal single_instance = null;

    private RestaurantTerminal() {
        System.out.println("Welcome to The Dream Team's Diner!");
    }

    public static RestaurantTerminal getInstance()
    {
        if (single_instance == null)
            single_instance = new RestaurantTerminal();

        return single_instance;
    }

    public void run() {
        restaurantMenus = RestaurantInit.initMenus();
        stock = RestaurantInit.initStock(stockCapacity);
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
        System.out.println("\nEnter a number to choose what you'd like to do:");
        int choice;

        if(Objects.equals(user.getUserType(), "customer")) {
            System.out.println("You have " +  ((Customer) user).getLoyaltyPoints(user.getIdNum()) + " loyalty points.");
            System.out.println("1. Place an order 2. View Menus 3. View Previous Orders 4. Settings 5. Logout 6. Quit");
            choice = UiUtils.getInput(1, 6);
            switch(choice) {
                case 1:
                    if(businessHours.isOpenNow()) {
                        double cost = user.placeOrder(user.getIdNum(), restaurantMenus, (Stock) stock);
                        if(cost > 0) {
                            addLoyaltyPoints(cost, user.getIdNum());
                            stock = null;
                            stock = RestaurantInit.initStock(stockCapacity);
                        }
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
                choice = UiUtils.getInput(1, 7);
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
                choice = UiUtils.getInput(1, 4);
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

    private void addLoyaltyPoints(double cost, int userId) {
        if(cost > 0) {
            int loyaltyPointsEarned = (int) (cost / 10);
            if(loyaltyPointsEarned > 0) {
                int balance = ((Customer) user).getLoyaltyPoints(userId);
                JSONObject newPoints = new JSONObject();
                newPoints.put("user_id", userId);
                newPoints.put("loyalty_points", balance + loyaltyPointsEarned);
                Database.updateTable("loyalty", newPoints);
            }
        }
    }

    private void staffPlaceOrder() {
        System.out.println("Enter the user id of the user you'd like to place an order for");
        System.out.println("B = go back");
        String idChoice = scanner.nextLine();
        if(UiUtils.inputB(idChoice)) return;
        int id = Integer.parseInt(idChoice);
        if(businessHours.isOpenNow()) {
            double cost = user.placeOrder(id, restaurantMenus, (Stock) stock);
            if(cost > 0) {
                stock = null;
                stock = RestaurantInit.initStock(stockCapacity);
            }
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
        System.out.println("1. View stock 2. Add New Stock Item 3. Update Stock Item Details (including count) " +
                "4. Remove stock item 5. Order Stock \nB = go back");
        int choice = UiUtils.getInput(1, 5);
        switch (choice) {
            case 1:
                System.out.println(stock.show());
                break;
            case 2:
                ((Stock) stock).addNewStockItem();
                stock = null;
                stock = RestaurantInit.initStock(stockCapacity);
                break;
            case 3:
                ((Stock) stock).updateStockItemDetails();
                stock = null;
                stock = RestaurantInit.initStock(stockCapacity);
                break;
            case 4:
            case 5:
                System.out.println("Sorry, this use case was not implemented");
                break;
        }
    }

    private void employeeManagement() {
        System.out.println("1. Add Employee 2. View Employees 3. Edit Employee 4. Remove Employee\nB = go back");
        int choice = UiUtils.getInput(1, 4);
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
        int choice = UiUtils.getInput(1, 4);
        switch(choice) {
            case 1:
                restaurantMenus.add(((Manager) user).makeMenu());
                break;
            case 2:
                int menuID = user.viewMenu(restaurantMenus, "edit:");
                for(Menu menu : restaurantMenus) {
                    if(menu.getId() == menuID) {
                        menu.editMenu(stock);
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
        int choice = UiUtils.getInput(0,1);
        if(choice == 1) {
            String newPass = UiUtils.getNewPassword();
            JSONObject userNewPass = new JSONObject();
            userNewPass.put("password", newPass);
            userNewPass.put("user_id", user.getIdNum());
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
        int numChoice = UiUtils.getInput(1, 3);

        switch (numChoice) {
            case 1:
                userLogin.displayLoginPrompt();
                if(userLogin.isSuccessfulLogin()) {
                    user = User.createUser(false, userLogin.getEmail());
                }
                break;
            case 2:
                // UserType can only be customer - manager creates new staff
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
        if(user instanceof Customer) Customer.addObservable((Customer) user, businessHours);
    }
}
