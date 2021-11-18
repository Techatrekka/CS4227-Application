package com.company.order;

import com.company.menu.MenuItem;
import org.json.JSONObject;

import java.util.ArrayList;

public class Order{
    private int orderID;
    private ArrayList<MenuItem> menuItems = new ArrayList<>();
    private double totalCost;

    public Order() {}
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

    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
    }

    public double getTotalCost(){
        for(MenuItem item : menuItems) {
            totalCost += item.getPrice();
        }
        return this.totalCost;
    }

     public void setMenuItems(ArrayList<MenuItem> menuItems){
         this.menuItems = menuItems;
     }

     public ArrayList<MenuItem> getMenuItems(){
         return this.menuItems;
     }

     public String toString() {
        return "Order id: " + this.getOrderID() + "\tTotal cost: €" + this.getTotalCost() + "\nOrder items:\n" +
                getMenuItems();
     }

}
