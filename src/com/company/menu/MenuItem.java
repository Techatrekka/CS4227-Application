package com.company.menu;

import java.util.List;

public abstract class MenuItem{
    public String name;
    public double price;
    public int id;
    List<String> allergens;
    String description;

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
    @Override
    public String toString(){
        StringBuilder allergenList = new StringBuilder();
        if(allergens.size() > 0) {
            for(String item : allergens) {
                allergenList.append(item).append("\t\t");
            }
        }
        if(description == null) description = "";
        return  "\n\tId: " + this.getID() + "\n\tName: " + this.getName() +
                "\n\tPrice: €" + this.getPrice() + "\n\tDescription: " + this.getDescription() + "\n\tAllergens: " + allergenList + "\n";
    }
}
