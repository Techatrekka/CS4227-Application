package com.company.menu;

import com.company.restaurant.Database;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class MenuItem{
    public String name;
    public double price;
    public int id;
    List<String> allergens;
    String description;
    List<String> ingredients;

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
    public int getID(){
        return id;
    }
    public void setID(int id){
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }

    public List<String> getAllergens() {
        return allergens;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    boolean ingredientsInStock() {
        for(String i : ingredients) {
            int id = Integer.parseInt(i);
            ArrayList<String> cols = new ArrayList<String>() {{
                add("stock_item_id");
                add("count");
            }};
            JSONObject ingredientDetails = Database.readFromTable("stockitems", id, cols, "stock_item_id");
            if (ingredientDetails.getInt("count") < 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString(){
        StringBuilder allergenList = new StringBuilder();
        if (allergens.size() > 0) {
            for (String item : allergens) {
                allergenList.append(item).append("\t\t");
            }
        }
        if (description == null) description = "";
        return "\n\tId: " + this.getID() + "\n\tName: " + this.getName() +
                "\n\tPrice: €" + this.getPrice() + "\n\tDescription: " + this.getDescription() + "\n\tAllergens: " + allergenList + "\n";

    }

}
