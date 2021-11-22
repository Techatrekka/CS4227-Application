package com.company.menu;

public class Wedges extends MenuItemDecorator {
    MenuItem item;
    public Wedges(MenuItem item){
        this.item = item;
    }

    public int getID() {
        return item.id;
    }

    public String toString() {
        item.description += " + Wedges";
        return item.toString();
    }
    public String getDescription(){
        return item.getDescription() + " + Wedges";
    }
    public double getPrice(){
        return item.getPrice() + 3.45;
    }
}
