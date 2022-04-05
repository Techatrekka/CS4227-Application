package com.company.menu;

public class Chips extends MenuItemDecorator {
    private MenuItem item;

    public Chips(MenuItem item){
        this.item = item;
    }

    @Override
    public String getDescription(){
        return decorateWithChips();
    }
    private String decorateWithChips() {
        String chipsText =  "+ Chips";
        return chipsText;
    }

    @Override
    public double getPrice(){
        return item.getPrice() + 2.85;
    }

    @Override
    public int getID() {
        return item.getID();
    }

    @Override
    public String toString() {
        return "\n\tId: " + item.getID() + "\n\tName: " + item.getName() + "\n\tPrice: " + item.getPrice() +
                "\n\tDescription: " + item.getDescription() + this.getDescription() + "\n\tAllergens: " +
                item.getAllergens();
    }
}
