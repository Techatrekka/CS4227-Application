package com.company.order;

import com.company.menu.MenuItem;
import org.json.JSONObject;

import java.util.HashMap;

public class Order{
    private int orderID;
    HashMap<Integer, MenuItem> menuItems = new HashMap<>();
    private double totalCost;

    public Order() {
        totalCost = 0.0;
    }
    public Order(JSONObject orderDetails) {
        this.orderID = orderDetails.getInt("order_id");
        this.totalCost = Double.parseDouble(orderDetails.getString("total_cost"));
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

    public void addMenuItem(int menuId, MenuItem item) {
        menuItems.put(menuId, item);
    }

    public int getOrderItemMenuId(int itemId) {
        for (int i : menuItems.keySet()) {
            if(menuItems.get(i).getID() == itemId) return i;
        }
        return -1;
    }

    public double getTotalCost(){
        return this.totalCost;
    }

    public double calcCostOfItems() {
        double cost = 0.0;
        for (int i : menuItems.keySet()) {
           cost += menuItems.get(i).getPrice();
        }
        return cost;
    }

     public void setMenuItems( HashMap<Integer, MenuItem> menuItems){
         this.menuItems = menuItems;
     }

     public  HashMap<Integer, MenuItem> getMenuItems(){
         return this.menuItems;
     }

     public String toString() {
        StringBuilder items = new StringBuilder();
         for (int i : menuItems.keySet()) {
             items.append(menuItems.get(i));
         }
        return "Order id: " + this.getOrderID() + "\tTotal cost: €" + this.getTotalCost() + "\nOrder items:\n" +
                items;
     }

}
