package com.company.order;

import java.util.ArrayList;

public class Order{
    private int orderID;
    // private ArrayList<MenuItems> menuItems;
    private double totalCost;
    
    public void setOrderID(int orderID){
        this.orderID = orderID;
    }

    public int getOrderID(){
        return this.orderID;
    }

    public void setTotalCost(double totalCost){
        this.totalCost = totalCost;
    }

    public double getTotalCose(){
        return this.totalCost;
    }

    // public void setMenuItems(ArrayList<MenuItems> menuItems){
    //     this.menuItems = menuItems;
    // }

    // public ArrayList<MenuItems> getMenuItems(){
    //     return this.menuItems
    // }

}
