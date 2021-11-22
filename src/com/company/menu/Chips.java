package com.company.menu;

public class Chips extends MenuItemDecorator {
    MenuItem item;
    public Chips(MenuItem item){
        this.item = item;
    }
    public String getDescription(){
        return item.getDescription() + " + Chips";
    }
    public double getPrice(){
        return item.getPrice() + 2.85;
    }
    public int getID() {
        return item.id;
    }
    public String toString() {
        item.description += " + Chips";
        return item.toString();
    }
}
