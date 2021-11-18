package com.company.menu;

import org.json.JSONObject;

import java.util.ArrayList;

public class Beverage extends MenuItem {
    boolean alcoholic;
    public Beverage(JSONObject bevDetails){
        super.id = bevDetails.getInt("beverage_id");
        super.name = bevDetails.getString("name");
        super.price = bevDetails.getDouble("price");
        this.alcoholic = bevDetails.getBoolean("alcoholic");
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
            return super.toString()+"\n\tAlcoholic: Yes\n";
        }
        else{
            return super.toString()+"\n\tAlcoholic: No\n";
        }
    }
}
