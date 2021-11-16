package com.company.menu;

import java.util.ArrayList;
import java.util.List;

public class Dish extends MenuItem{
    String description;
    List<String> allergens;
    public Dish(int id, String name, double price, String description, List<String> allergens){
        super.id = id;
        super.name = name;
        super.price = price;
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

    @Override
    public String toString(){
        StringBuilder allergenList = new StringBuilder();
        if(allergens.size() > 0) {
            for(String item : allergens) {
                allergenList.append(item.toString()).append("\n");
            }
        }
        return super.toString() + "\nDescription: " + description +
                                  "\nAllergens: " + allergenList;
    }
}
