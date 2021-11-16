package com.company.menu;

import java.util.ArrayList;

public class Beverage extends MenuItem {
    boolean alcoholic;
    public Beverage(int id, String name, double price, boolean alcoholic){
        super.id = id;
        super.name = name;
        super.price = price;
        this.alcoholic = alcoholic;
    }
    @Override
    public double getPrice() {
        return super.price;
    }

    @Override
    public void setPrice(double price) {
        super.price = price;

    }

    @Override
    public String getName() {
        return super.name;
    }
    @Override
    public void setName(String name) {
        super.name = name;
    }
    public String toString(){
        if(alcoholic){
            return super.toString()+"\nThis beverage is alcoholic.";
        }
        else{
            return super.toString()+"\nThis beverage is non-alcoholic.";
        }
    }
}
