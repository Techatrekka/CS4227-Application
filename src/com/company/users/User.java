package com.company.users;

import com.company.Database;
import com.company.menu.*;
import com.company.order.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.crypto.Data;
import java.util.*;

public abstract class User {
    private String userType;
    private int idNum;
    private String fullName;
    private String email;
    Scanner scanner = new Scanner(System.in);

    public int viewMenu(ArrayList<Menu> restaurantMenus, String toDo){
        for(Menu menu : restaurantMenus) {
            System.out.println(menu);
        }

        if(!toDo.equals("view:")) {
            System.out.println("Enter the id of the menu to " + toDo);
            int menuID = scanner.nextInt();
            scanner.nextLine();
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
            System.out.println("Would you like to order a meal deal, which includes a set meal and drink? y/n");
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
                            for(MenuItem menuItem : menu.getMenuItems()) {
                                if(menuItem.getID() == Integer.parseInt(choice)) {
                                    item = getOrderItem(menuItem instanceof Dish, Integer.parseInt(choice));
                                }
                            }
                            System.out.println("Would you like a small, regular or large? S/R/L");
                            choice = scanner.nextLine();
                            if(choice.equalsIgnoreCase("s")) {
                                MenuItem item2 = new SmallItemDecorator(item);
                            } else if(choice.equalsIgnoreCase("l")) {
                                item = new LargeItemDecorator(item);
                            }
                            newOrder.addMenuItem(item);
                        }
                    }
                }
            }
            System.out.println("Would you like to order anything else? y/n");
            choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("n")) addToOrder = false;
        }
        JSONObject orderDetails = new JSONObject();
        newOrder.setTotalCost(setMealCost + newOrder.getTotalCost());
        orderDetails.put("total_cost", String.valueOf(newOrder.getTotalCost()));
        orderDetails.put("user_id", userId);
        int orderId = Database.writeToTable("order", orderDetails);
        for(MenuItem item : newOrder.getMenuItems()) {
            JSONObject orderLineDetails = new JSONObject();
            orderLineDetails.put("menu_item", item.getID());
            orderLineDetails.put("order_id", orderId);
            orderLineDetails.put("food", item instanceof Dish);
            Database.writeToTable("orderlineitem", orderLineDetails);
        }

        int time = (int) (Math.random() * 30) + 6;
        orderDetails.put("total_cost", newOrder.getTotalCost());
        Database.updateTable("order", orderDetails);
        System.out.println("Your order will be ready for collection in " + time + " minutes and will cost €" + newOrder.getTotalCost());
    }

    private MenuItem getOrderItem(boolean isFood, int id) {
        MenuItem item;
        ArrayList<String> cols = new ArrayList<>();
        cols.add("name");
        cols.add("price");
        if(isFood) {
            cols.add("description");
            cols.add("allergens");
            cols.add("dish_id");
            JSONObject itemDetails = Database.readFromTable("dishes", id, cols, "dish_id", -1, "");
            item = new Dish(itemDetails);
        } else {
            cols.add("alcoholic");
            cols.add("beverage_id");
            JSONObject itemDetails = Database.readFromTable("beverages", id, cols, "beverage_id", -1, "");
            item = new Beverage(itemDetails);
        }
        return item;
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
                    MenuItem item = getOrderItem(orderItemDetails.getBoolean("food"), orderItemDetails.getInt("menu_item"));
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
        UserFactory userFactory = new UserFactory();
        List<String> cols = new ArrayList<>();
        // JSON for extra attributes for user depending on whether they're employees or customers
        JSONObject extraAttributes = new JSONObject();
        User user;
        JSONObject userDetailsJson;
        userDetailsJson = Database.readFromUserTable(email, null);
        extraAttributes.put("user_id", userDetailsJson.getInt("user_id"));

        // if userLogin email is empty then this is a newly registered user
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
        user = userFactory.createUser(userDetailsJson);
        return user;
    }

}
