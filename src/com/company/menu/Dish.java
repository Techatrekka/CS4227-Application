package com.company.menu;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class Dish extends MenuItem{
    private String description;

    public Dish(JSONObject dishDetails){
        super.id = dishDetails.getInt("menu_item");
        super.name = dishDetails.getString("name");
        super.price = dishDetails.getDouble("Price");
        super.description = dishDetails.getString("Description");
        String[] dishAllergenArr = dishDetails.getString("Allergens").split(",");
        super.allergens = Arrays.asList(dishAllergenArr);
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
