package com.company.menu;

import java.util.ArrayList;

public abstract class MenuItem{
    public String name;
    public double price;
    public int ID;
    //public ArrayList<>

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getMenuID(){
        return ID;
    }
    public void setMenuID(int ID){
        this.ID = ID;
    }
}
