package com.company.menu;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class Dish extends MenuItem{
    protected String description;
    protected List<String> allergens;
    public Dish(JSONObject dishDetails){
        super.id = dishDetails.getInt("dish_id");
        super.name = dishDetails.getString("name");
        super.price = dishDetails.getDouble("price");
        super.description = dishDetails.getString("description");
        String dishAllergens = dishDetails.getString("allergens");
        String[] dishAllergenArr = dishAllergens.split(",");
        this.allergens = Arrays.asList(dishAllergenArr);
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
                allergenList.append(item).append("\n");
            }
        }
        if(description == null) description = "";
        return super.toString() + "\n\tDescription: " + description +
                                  "\n\tAllergens: " + allergenList;
    }
}
