package com.company.users;

import com.company.Database;
import com.company.menu.Menu;
import com.company.order.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

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
        boolean addToOrder = true;
        Order newOrder = new Order();
        double totalCost = 0.0;
        while(addToOrder) {
            int menuId = viewMenu(restaurantMenus, "order from:");
            for(Menu menu : restaurantMenus) {
                if(menu.getId() == menuId) System.out.println("\n" + menu);
            }
            System.out.println("Enter the id of the item you'd like to order");

            System.out.println("Would you like to order anything else? y/n");
            String choice = scanner.nextLine();
            if(choice.equalsIgnoreCase("n")) addToOrder = false;
        }
        int time = (int) (Math.random() * 30) + 6;
        newOrder.setTotalCost(totalCost);
        System.out.println("Your order will be ready for collection in " + time + " minutes");
    }

    public void cancelOrder(int userId, int orderId){
        // Implement use case
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
