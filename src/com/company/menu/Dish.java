package com.company.menu;

import org.json.JSONObject;

import java.util.Arrays;

public class Dish extends MenuItem {

    public Dish(JSONObject dishDetails){
        super.id = dishDetails.getInt("menu_item");
        super.name = dishDetails.getString("name");
        super.price = dishDetails.getDouble("Price");
        super.description = dishDetails.getString("Description");
        super.allergens = Arrays.asList(dishDetails.getString("Allergens").split(","));
        super.ingredients = Arrays.asList(dishDetails.getString("Ingredients").split(","));
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
