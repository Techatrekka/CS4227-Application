package com.company.order;

import com.company.menu.Beverage;
import com.company.menu.Dish;
import com.company.menu.Menu;
import com.company.menu.MenuItem;
import com.company.restaurant.Database;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Order implements Visitable {
    private int orderID;
    private ArrayList<MenuItem> menuItems = new ArrayList<>();
    private double totalCost;

    public Order(double totalCost) {
        this.totalCost = totalCost;
    }
    public Order(JSONObject orderDetails) {
        this.orderID = orderDetails.getInt("order_id");
        this.totalCost = Double.parseDouble(orderDetails.getString("total_cost"));
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
    
    public void setOrderID(int orderID){
        this.orderID = orderID;
    }

    public int getOrderID(){
        return this.orderID;
    }

    public void setTotalCost(double totalCost){
        this.totalCost = totalCost;
    }

    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    public MenuItem addSetMenuItem(int itemId) {
        ArrayList<String> cols = new ArrayList<>();
        cols.add("menu_item");
        cols.add("Alcoholic");
        cols.add("Description");
        cols.add("Ingredients");
        cols.add("Price");
        cols.add("Allergens");
        cols.add("isFood");
        cols.add("name");
        JSONObject itemDetails = Database.readFromTable("menuitem", itemId, cols, "menu_item");
        if (itemDetails.getBoolean("isFood")) {
            return new Dish(itemDetails);
        } else {
            return new Beverage(itemDetails);
        }
    }

    public double getTotalCost(){
        return this.totalCost;
    }

    public double calcCostOfItems() {
        double cost = 0.0;
        for (MenuItem i : menuItems) {
           cost += i.getPrice();
        }
        return cost;
    }

     public void setMenuItems( ArrayList menuItems){
         this.menuItems = menuItems;
     }

     public  ArrayList<MenuItem> getMenuItems(){
         return this.menuItems;
     }

     public String toString() {
        StringBuilder items = new StringBuilder();
         for (MenuItem i : menuItems) {
             items.append(i);
         }
        return "Order id: " + this.getOrderID() + "\tTotal cost: €" + this.getTotalCost() + "\nOrder items:\n" +
                items;
     }

}
