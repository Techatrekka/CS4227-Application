package com.company.menu;

import java.time.LocalDate;

public class SpecialMenu extends Menu {
    protected double discount;

    public SpecialMenu(int id, String name, String description, LocalDate dateCreated, double discount, String menuItems){
        super(id, name, description, dateCreated, menuItems);
        this.discount = discount;
    }

    public boolean isSpecialMenu(){
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "\nDiscount on menu: " + this.discount + "\n";
    }
}
