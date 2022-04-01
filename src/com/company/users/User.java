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

    public int viewMenu(ArrayList<Menu> restaurantMenus, String option){
        for(Menu menu : restaurantMenus) {
            if(userType.equals("customer") && menu.getMenuItems().size() > 0) {
                System.out.println(menu);
            } else if(userType.equals("employee")) {
                System.out.println(menu);
            }
        }
        if(userType.equals("employee")) System.out.println("Note: Customers will only see the menus that have items in them. " +
                "Managers can use the edit menu option to add menu items.");

        if(!option.equals("view:")) {
            return chooseMenu(option, restaurantMenus);
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

    public double placeOrder(int userId, ArrayList<Menu> restaurantMenus, Stock stock){
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
                        System.out.println("Enter the id of the item you'd like to order");
                        choice = scanner.nextLine();
                        while(!UiUtils.isValid(choice, -1, -1)) {
                            choice = scanner.nextLine();
                        }
                        MenuItem item = null;
                        for(MenuItem menuItem : menu.getMenuItems()) {
                            if(menuItem.getID() == Integer.parseInt(choice)) {
                                item = chooseSideDish(Integer.parseInt(choice),  menuItem instanceof Dish);
                            }
                        }
                        newOrder.addMenuItem(item);
                    }
                }
            }
            System.out.println("Would you like to order anything else? y/n");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("n")) addToOrder = false;
        }
        JSONObject orderDetails = new JSONObject();
        if (newOrder == null){
            newOrder = new Order(0.0);
        }
        newOrder.setTotalCost(newOrder.calcCostOfItems() + setMealCost);
        orderDetails.put("total_cost", String.valueOf(newOrder.getTotalCost()));
        orderDetails.put("user_id", userId);

        int orderId = Database.writeToTable("order", orderDetails);

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

        if(stock.ingredientsInStock(orderStockItems)) {
            if(newOrder.getTotalCost() > 0) {
                System.out.println("Your order:");
                for(MenuItem item : newOrder.getMenuItems()) {
                    JSONObject orderItemDetails = new JSONObject();
                    orderItemDetails.put("menu_item", item.getID());
                    orderItemDetails.put("order_id", orderId);
                    System.out.println(item);
                    Database.writeToTable("orderlineitem", orderItemDetails);
                }
                stock.removeStockItemsForOrder(orderStockItems);
                int time = (int) (Math.random() * 30) + 6;
                ShoppingCart cart = new ShoppingCart();
                cart.items.add(newOrder);
                double deliveryCost = cart.accept();
                System.out.println("Your order will be delivered in " + time + " minutes and will cost €" + String.format("%.2f", newOrder.getTotalCost()) + " + delivery fee €" + String.format("%.2f", deliveryCost));
            } else {
                System.out.println("The order was cancelled.");
                newOrder.setTotalCost(0);
                Database.deleteFromTable("order", "order_id", orderId);
            }
        } else {
            System.out.println("Sorry, but there aren't enough ingredients in stock for all your order items.\n" +
                    "The order was cancelled, please try again.");
            newOrder.setTotalCost(0);
            Database.deleteFromTable("order", "order_id", orderId);
        }

        return newOrder.getTotalCost();
    }

    private JSONObject getOrderItem(int menuItemId) {
        JSONObject menuItemDetails;
        ArrayList<String> cols = new ArrayList<String>() {{
            add("name");
            add("Price");
            add("Description");
            add("Ingredients");
            add("isFood");
            add("Allergens");
            add("menu_item");
            add("Alcoholic");
        }};
        menuItemDetails = Database.readFromTable("menuitem", menuItemId, cols, "menu_item");
        return menuItemDetails;
    }

    public void getOrders() {
        JSONArray allUserOrders = Database.readAllFromTable("order", this.getIdNum(), "user_id", "");
        if(allUserOrders.length() > 0) {
            for (Object orderObj : allUserOrders){
                JSONObject orderDetails = (JSONObject)orderObj;
                Order order = new Order(orderDetails);
                JSONArray allOrderItems = Database.readAllFromTable("orderlineitem", orderDetails.getInt("order_id"), "order_id", "");
                for (Object orderItemObj : allOrderItems) {
                    JSONObject orderItemDetails = (JSONObject) orderItemObj;
                    JSONObject itemDetails = getOrderItem(orderItemDetails.getInt("menu_item"));
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
        List<String> cols = new ArrayList<>();
        // JSON for extra attributes for user depending on whether they're employees or customers
        JSONObject extraAttributes = new JSONObject();
        JSONObject userDetailsJson;
        userDetailsJson = Database.readFromUserTable(email, null);
        extraAttributes.put("user_id", userDetailsJson.getInt("user_id"));

        // if it's a new user, we need to write loyalty points to the loyalty table
        if(isNewUser) {
            if(Objects.equals(userDetailsJson.getString("user_type"), "customer")) {
                extraAttributes.put("loyalty_points", 0);
                Database.writeToTable("loyalty", extraAttributes);
                userDetailsJson.put("loyalty_points", 0);
            }
        } else {
            // If it's an existing customer, read their loyalty points value from the database
            if(Objects.equals(userDetailsJson.getString("user_type"), "customer")) {
                cols.add("loyalty_points");
                extraAttributes = Database.readFromTable("loyalty", userDetailsJson.getInt("user_id"), cols, "user_id");
                userDetailsJson.put("loyalty_points", extraAttributes.getInt("loyalty_points"));
            // If it's an existing employee, read their salary and employee type from the database
            } else if(Objects.equals(userDetailsJson.getString("user_type"), "employee")) {
                cols.add("salary");
                cols.add("employee_type");
                JSONObject employeeTypeSalary = Database.readFromTable("employeesalary", userDetailsJson.getInt("user_id"), cols, "user_id");
                userDetailsJson.put("salary", employeeTypeSalary.getDouble("salary"));
                userDetailsJson.put("employee_type", employeeTypeSalary.getString("employee_type"));
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
