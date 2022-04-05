package com.company.users;

import com.company.order.ShoppingCart;
import com.company.order.SpecialOrder;
import com.company.restaurant.Database;
import com.company.menu.*;
import com.company.order.Order;

import com.company.stock.Stock;
import com.company.ui.UiUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public abstract class User {
    private String userType;
    private int idNum;
    private String fullName;
    private String email;
    private Scanner scanner = new Scanner(System.in);
    private static String userIdKey = "user_id";
    private String orderTable = "order";
    private static String loyaltyPointsKey = "loyalty_points";
    private static String loyaltyKey = "loyalty";
    private String orderKey = "order_id";
    private String menuKey = "menu_item";

    public int viewMenu(List<Menu> restaurantMenus, String option){
        String customerKey = "customer";
        String employeeKey = "employee";
        for(Menu menu : restaurantMenus) {
            if(userType.equals(customerKey) && !menu.getMenuItems().isEmpty()) {
                System.out.println(menu);
            } else if(userType.equals(employeeKey)) {
                System.out.println(menu);
            }
        }
        if(userType.equals(employeeKey)) System.out.println("Note: Customers will only see the menus that have items in them. " +
                "Managers can use the edit menu option to add menu items.");

        if(!option.equals("view:")) {
            return chooseMenu(option, (ArrayList<Menu>) restaurantMenus);
        }
        return -1;
    }

    int chooseMenu(String option, ArrayList<Menu> restaurantMenus) {
        System.out.println("Enter the id of the menu to " + option);
        int menuID = scanner.nextInt();
        scanner.nextLine();
        for(Menu menu : restaurantMenus) {
            if(menu.getId() == menuID) {
                System.out.println(menu);
            }
        }
        return menuID;
    }

    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public String getUserType() {
        return userType;
    }

    public int getIdNum() {
        return idNum;
    }

    public String getFullName() {
        return fullName;
    }

    public double placeOrder(int userId, List<Menu> restaurantMenus, Stock stock){
        Order newOrder = null;
        System.out.println("Delivery costs €0.40 per item for orders under €10, €0.20 per item for orders under €20, and is free for orders over €20.");
        boolean addToOrder = true;
        double setMealCost = 0.0;
        while(addToOrder) {
            System.out.println("Would you like to order a meal deal, which includes a set meal and drink? Y / N");
            String choice = scanner.nextLine();
            if(choice.equalsIgnoreCase("y")) {
                newOrder = new SpecialOrder(0.0, 0.05);
                SetMeal meal = buildMeal();
                for(int id : meal.getMenuItemIds()) {
                    MenuItem item = newOrder.addSetMenuItem(id);
                    newOrder.addMenuItem(item);
                }
            } else {
                newOrder = new Order(0.0);
                System.out.println("Press any key to view menus to order a la carte.");
                scanner.nextLine();
                int menuId = viewMenu(restaurantMenus, "order from:");
                for(Menu menu : restaurantMenus) {
                    if (menu.getId() == menuId) {
                        MenuItem item = chooseMenuItem(menu);
                        newOrder.addMenuItem(item);
                    }
                }
            }
            System.out.println("Would you like to order anything else? y/n");
            choice = scanner.nextLine();
            addToOrder = continueOrder(choice);
        }
        JSONObject orderDetails = new JSONObject();
        newOrder.setTotalCost(newOrder.calcCostOfItems() + setMealCost);
        orderDetails.put("total_cost", String.valueOf(newOrder.getTotalCost()));
        orderDetails.put(userIdKey, userId);

        int orderId = Database.writeToTable(orderTable, orderDetails);

        checkStockAndCompleteOrder(newOrder, orderId, stock, userId);

        return newOrder.getTotalCost();

    }

    private boolean continueOrder(String choice) {
        return !choice.equalsIgnoreCase("n");
    }

    private HashMap<Integer, Integer> getOrderStockItems(Order newOrder) {
        HashMap<Integer, Integer> orderStockItems = new HashMap<>();
        for(MenuItem item : newOrder.getMenuItems()) {
            List<String> stockItems = item.getIngredients();
            for(String s : stockItems) {
                if(orderStockItems.containsKey(Integer.parseInt(s))) {
                    orderStockItems.put(Integer.parseInt(s), orderStockItems.get(Integer.parseInt(s)) + 1);
                } else {
                    orderStockItems.put(Integer.parseInt(s), 1);
                }
            }
        }
        return orderStockItems;
    }

    private void checkStockAndCompleteOrder(Order newOrder, int orderId, Stock stock, int userId) {
        LoyaltyStrategy loyaltyPoints;
        HashMap<Integer, Integer> orderStockItems = getOrderStockItems(newOrder);

        if(stock.ingredientsInStock(orderStockItems)) {
            if(newOrder.getTotalCost() > 0) {
                printOrderLineItems(newOrder, orderId);

                stock.removeStockItemsForOrder(orderStockItems);
                int time = (int) (Math.random() * 30) + 6;
                ShoppingCart cart = new ShoppingCart();
                cart.items.add(newOrder);
                double deliveryCost = cart.accept();

                System.out.println("Would you like to apply your loyalty discount? Y = yes, any other key = No");
                String choice = scanner.nextLine();
                if(choice.equalsIgnoreCase("y")) {
                    loyaltyPoints = new ApplyDiscount();
                } else {
                    loyaltyPoints = new DoNotApplyDiscount();
                }
                double pointsSpent = loyaltyPoints.applyLoyaltyDiscount(userId, newOrder.getTotalCost()) / 0.01;
                if(newOrder.getTotalCost() - loyaltyPoints.applyLoyaltyDiscount(userId, newOrder.getTotalCost()) < 0) {
                    double remainder = (loyaltyPoints.applyLoyaltyDiscount(userId, newOrder.getTotalCost()) - newOrder.getTotalCost()) / 0.01;
                    pointsSpent = (pointsSpent - remainder);
                    newOrder.setTotalCost(0.0);
                }
                int pointsToAdd = (int) (Math.round(newOrder.getTotalCost()) * 5);
                ArrayList<String> cols = new ArrayList<>();
                cols.add(loyaltyPointsKey);
                cols.add("loyalty_id");
                newOrder.setTotalCost(newOrder.getTotalCost() - loyaltyPoints.applyLoyaltyDiscount(userId, newOrder.getTotalCost()));
                JSONObject loyaltyPointDetails = Database.readFromTable(loyaltyKey, userId, cols, userIdKey);
                loyaltyPointDetails.put(loyaltyPointsKey, loyaltyPointDetails.getInt(loyaltyPointsKey) + pointsToAdd - pointsSpent);
                Database.updateTable(loyaltyKey, loyaltyPointDetails);
                System.out.println("Points added " + pointsToAdd + ", points spent " + pointsSpent);

                System.out.println("Your order will be delivered in " + time + " minutes and will cost €" + String.format("%.2f", newOrder.getTotalCost()) + " + delivery fee €" + String.format("%.2f", deliveryCost));
            } else {
                System.out.println("The order was cancelled.");
                newOrder.setTotalCost(0);
                Database.deleteFromTable(orderTable, orderKey, orderId);
            }
        } else {
            System.out.println("Sorry, but there aren't enough ingredients in stock for all your order items.\n" +
                    "The order was cancelled, please try again.");
            newOrder.setTotalCost(0);
            Database.deleteFromTable(orderTable, orderKey, orderId);
        }
    }

    private void printOrderLineItems(Order newOrder, int orderId) {
        System.out.println("Your order:");
        for(MenuItem item : newOrder.getMenuItems()) {
            JSONObject orderItemDetails = new JSONObject();
            orderItemDetails.put(menuKey, item.getID());
            orderItemDetails.put(orderKey, orderId);
            System.out.println(item);
            Database.writeToTable("orderlineitem", orderItemDetails);
        }
    }

    private MenuItem chooseMenuItem(Menu menu) {
        System.out.println("Enter the id of the item you'd like to order");
        String choice = scanner.nextLine();
        while(!UiUtils.isValid(choice, -1, -1)) {
            choice = scanner.nextLine();
        }
        MenuItem item = null;
        for(MenuItem menuItem : menu.getMenuItems()) {
            if(menuItem.getID() == Integer.parseInt(choice)) {
                item = chooseSideDish(Integer.parseInt(choice),  menuItem instanceof Dish);
            }
        }
        return item;
    }

    private JSONObject getOrderItem(int menuItemId) {
        JSONObject menuItemDetails;
        ArrayList<String> cols = new ArrayList<>();
        cols.add("name");
        cols.add("Price");
        cols.add("Description");
        cols.add("Ingredients");
        cols.add("isFood");
        cols.add("Allergens");
        cols.add(menuKey);
        cols.add("Alcoholic");

        menuItemDetails = Database.readFromTable("menuitem", menuItemId, cols, menuKey);
        return menuItemDetails;
    }

    public void getOrders() {
        JSONArray allUserOrders = Database.readAllFromTable(orderTable, this.getIdNum(), userIdKey, "");
        if(allUserOrders.length() > 0) {
            for (Object orderObj : allUserOrders){
                JSONObject orderDetails = (JSONObject)orderObj;
                Order order = new Order(orderDetails);
                JSONArray allOrderItems = Database.readAllFromTable("orderlineitem", orderDetails.getInt(orderKey), orderKey, "");
                for (Object orderItemObj : allOrderItems) {
                    JSONObject orderItemDetails = (JSONObject) orderItemObj;
                    JSONObject itemDetails = getOrderItem(orderItemDetails.getInt(menuKey));
                    MenuItem item = itemDetails.getBoolean("isFood") ? new Dish(itemDetails) : new Beverage(itemDetails);
                    order.addMenuItem(item);
                }
                System.out.println(order);
            }
        } else {
            System.out.println("You have no previous orders");
        }

    }

    public void cancelOrder(int userId, int orderId){
        // Not implementing this use case
    }

    public static User createUser(boolean isNewUser, String email) {
        String employeeTypeKey = "employee_type";
        String salaryKey = "salary";
        String userTypeKey = "user_type";
        String customerUser = "customer";
        String employeeUser = "employee";
        List<String> cols = new ArrayList<>();
        // JSON for extra attributes for user depending on whether they're employees or customers
        JSONObject extraAttributes = new JSONObject();
        JSONObject userDetailsJson;
        userDetailsJson = Database.readFromUserTable(email, null);
        extraAttributes.put(userIdKey, userDetailsJson.getInt(userIdKey));

        // if it's a new user, we need to write loyalty points to the loyalty table
        if(isNewUser) {
            if(Objects.equals(userDetailsJson.getString(userTypeKey), customerUser)) {
                extraAttributes.put(loyaltyPointsKey, 0);
                Database.writeToTable(loyaltyKey, extraAttributes);
                userDetailsJson.put(loyaltyPointsKey, 0);
            }
        } else {
            // If it's an existing customer, read their loyalty points value from the database
            if(Objects.equals(userDetailsJson.getString(userTypeKey), customerUser)) {
                cols.add(loyaltyPointsKey);
                extraAttributes = Database.readFromTable(loyaltyKey, userDetailsJson.getInt(userIdKey), cols, userIdKey);
                userDetailsJson.put(loyaltyPointsKey, extraAttributes.getInt(loyaltyPointsKey));
            // If it's an existing employee, read their salary and employee type from the database
            } else if(Objects.equals(userDetailsJson.getString(userTypeKey), employeeUser)) {
                cols.add(salaryKey);
                cols.add(employeeTypeKey);
                JSONObject employeeTypeSalary = Database.readFromTable("employeesalary", userDetailsJson.getInt(userIdKey), cols, userIdKey);
                userDetailsJson.put(salaryKey, employeeTypeSalary.getDouble(salaryKey));
                userDetailsJson.put(employeeTypeKey, employeeTypeSalary.getString(employeeTypeKey));
            }
        }
        UserFactory userFactory = new UserFactory();
        return userFactory.createUser(userDetailsJson);
    }

    MenuItem chooseSideDish(int choice, boolean isFood) {
        MenuItem item = null;
        JSONObject itemDetails;
        System.out.println("Would you like chips, wedges, both, or neither with your order? C = chips, W = wedges, B = both, N = none");
        String sideChoice = scanner.nextLine();
        if(sideChoice.equalsIgnoreCase("c")) {
            itemDetails = getOrderItem(choice);
            item = isFood ? new Chips(new Dish(itemDetails)) : new Chips(new Beverage(itemDetails));
            System.out.println("Chips added");
        } else if(sideChoice.equalsIgnoreCase("w")) {
            itemDetails = getOrderItem(choice);
            item = isFood ? new Wedges(new Dish(itemDetails)) : new Wedges(new Beverage(itemDetails));
            System.out.println("Wedges added");
        } else if(sideChoice.equalsIgnoreCase("b")) {
            itemDetails = getOrderItem(choice);
            item = isFood ? new Wedges(new Chips(new Dish(itemDetails))) : new Wedges(new Chips(new Beverage(itemDetails)));
            System.out.println("Chips and wedges added");
        } else {
            itemDetails = getOrderItem(choice);
            item = isFood ? new Dish(itemDetails) : new Beverage(itemDetails);
            System.out.println("No side dishes added");
        }
        return item;
    }

    SetMeal buildMeal() {
        MealDirector director = new MealDirector();
        SetMealBuilder builder = null;
        System.out.println("Do you want to order a kids meal? Y / N");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("y")) {
            builder = new KidsMealBuilder();
        } else {
            builder = new AdultMealBuilder();
            System.out.println("Adult's meal deal has been ordered.");
        }

        SetMeal meal = director.createMeal(builder);
        System.out.println(meal);
        return meal;
    }
}
