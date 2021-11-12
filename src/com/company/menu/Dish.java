package com.company.menu;

import java.util.ArrayList;

public class Dish extends MenuItem{
    String description;
    ArrayList<String> allergens;
    public Dish(String name, double price, String description, int id, ArrayList<String> allergens){
        super.name = name;
        super.price = price;
        super.ID = id;
        this.description = description;
        this.allergens = allergens;
    }
    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
