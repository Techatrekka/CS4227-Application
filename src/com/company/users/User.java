package com.company.users;

import com.company.restaurant.Database;
import com.company.menu.*;
import com.company.order.Order;

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
            System.out.println(menu);
        }

        if(!option.equals("view:")) {
            System.out.println("Enter the id of the menu to " + option);
            int menuID = scanner.nextInt();
            scanner.nextLine();
            for(Menu menu : restaurantMenus) {
                if(menu.getId() == menuID) System.out.println(menu);
            }
            return menuID;
        }

        return -1;
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

    public void placeOrder(int userId, ArrayList<Menu> restaurantMenus){
        Order newOrder = new Order();
        boolean addToOrder = true;
        double setMealCost = 0.0;
        while(addToOrder) {
            System.out.println("Would you like to order a meal deal, which includes a set meal and drink? Y / N");
            String choice = scanner.nextLine();
            if(choice.equalsIgnoreCase("y")) {
                MealDirector director = new MealDirector();
                SetMealBuilder builder = null;
                System.out.println("Do you want to order a kids meal? Y / N");
                choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("y")) {
                    builder = new KidsMealBuilder();
                } else {
                    builder = new AdultMealBuilder();
                    System.out.println("Adult's meal has been ordered.");
                }
                SetMeal meal = director.createMeal(builder);
                setMealCost += meal.getMealPrice();
                System.out.println(meal);
            } else {
                int menuId = viewMenu(restaurantMenus, "order from:");
                for(Menu menu : restaurantMenus) {
                    if (menu.getId() == menuId) {
                        if (menu.getMenuItems().size() < 1) {
                            System.out.println("Sorry, this menu has no items you can order. Press any key to select a new menu.");
                            scanner.nextLine();
                        } else {
                            System.out.println("Enter the id of the item you'd like to order");
                            choice = scanner.nextLine();
                            MenuItem item = null;
                            JSONObject itemDetails;
                            for(MenuItem menuItem : menu.getMenuItems()) {
                                if(menuItem.getID() == Integer.parseInt(choice)) {
                                    System.out.println("Would you like chips, wedges, or both with your order? C = chips, W = wedges, B = both, N = none");
                                    String sideChoice = scanner.nextLine();
                                    if(sideChoice.equalsIgnoreCase("c")) {
                                        itemDetails = getOrderItem(menuItem instanceof Dish, Integer.parseInt(choice));
                                        item = menuItem instanceof Dish ? new Chips(new Dish(itemDetails)) : new Chips(new Beverage(itemDetails));
                                    } else if(sideChoice.equalsIgnoreCase("w")) {
                                        itemDetails = getOrderItem(menuItem instanceof Dish, Integer.parseInt(choice));
                                        item = menuItem instanceof Dish ? new Wedges(new Dish(itemDetails)) : new Wedges(new Beverage(itemDetails));
                                    } else if(sideChoice.equalsIgnoreCase("b")) {
                                        itemDetails = getOrderItem(menuItem instanceof Dish, Integer.parseInt(choice));
                                        item = menuItem instanceof Dish ? new Wedges(new Chips(new Dish(itemDetails))) : new Wedges(new Chips(new Beverage(itemDetails)));
                                    } else {
                                        itemDetails = getOrderItem(menuItem instanceof Dish, Integer.parseInt(choice));
                                        item = menuItem instanceof Dish ? new Dish(itemDetails) : new Beverage(itemDetails);
                                    }
                                }
                            }
                            newOrder.addMenuItem(menuId, item);
                        }
                    }
                }
            }
            System.out.println("Would you like to order anything else? y/n");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("n")) addToOrder = false;
        }
        JSONObject orderDetails = new JSONObject();
        newOrder.setTotalCost(newOrder.calcCostOfItems() + setMealCost);
        orderDetails.put("total_cost", String.valueOf(newOrder.getTotalCost()));
        orderDetails.put("user_id", userId);

        int orderId = Database.writeToTable("order", orderDetails);
        for(MenuItem item : newOrder.getMenuItems().values()) {
            JSONObject orderLineDetails = new JSONObject();
            JSONObject menuItem = Database.readFromTable("menuitem", item.getID(), Collections.singletonList("menu_item"), "dish_bev_id", newOrder.getOrderItemMenuId(item.getID()), "menu_id");
            orderLineDetails.put("menu_item", menuItem.getInt("menu_item"));
            orderLineDetails.put("order_id", orderId);
            orderLineDetails.put("food", item instanceof Dish);
            Database.writeToTable("orderlineitem", orderLineDetails);
        }

        int time = (int) (Math.random() * 30) + 6;
        System.out.println("Your order will be ready for collection in " + time + " minutes and will cost €" + newOrder.getTotalCost());
    }

    private JSONObject getOrderItem(boolean isFood, int id) {
        JSONObject itemDetails;
        ArrayList<String> cols = new ArrayList<>();
        cols.add("name");
        cols.add("price");
        cols.add("description");
        if(isFood) {
            cols.add("allergens");
            cols.add("dish_id");
            itemDetails = Database.readFromTable("dishes", id, cols, "dish_id", -1, "");
        } else {
            cols.add("alcoholic");
            cols.add("beverage_id");
            itemDetails = Database.readFromTable("beverages", id, cols, "beverage_id", -1, "");
        }
        return itemDetails;
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
                    JSONObject dishBevId = Database.readFromTable("menuitem", orderItemDetails.getInt("menu_item"), Collections.singletonList("dish_bev_id"), "menu_item", -1, "");
                    JSONObject itemDetails = getOrderItem(orderItemDetails.getBoolean("food"), dishBevId.getInt("dish_bev_id"));
                    MenuItem item = orderItemDetails.getBoolean("food") ? new Dish(itemDetails) : new Beverage(itemDetails);
                    order.addMenuItem(-1, item);
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
            if(Objects.equals(userDetailsJson.getString("user_type"), "customer")) {
                cols.add("loyalty_points");
                extraAttributes = Database.readFromTable("loyalty", userDetailsJson.getInt("user_id"), cols, "user_id", -1, "");
                userDetailsJson.put("loyalty_points", extraAttributes.getInt("loyalty_points"));
            } else if(Objects.equals(userDetailsJson.getString("user_type"), "employee")) {
                cols.add("salary");
                cols.add("employee_type");
                JSONObject employeeTypeSalary = Database.readFromTable("employeesalary", userDetailsJson.getInt("user_id"), cols, "user_id", -1, "");
                userDetailsJson.put("salary", employeeTypeSalary.getDouble("salary"));
                userDetailsJson.put("employee_type", employeeTypeSalary.getString("employee_type"));
            }
        }
        UserFactory userFactory = new UserFactory();
        return userFactory.createUser(userDetailsJson);
    }

}
