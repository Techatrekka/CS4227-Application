package com.company.menu;

public class beverage extends MenuItem {
    @Override
    public double getPrice() {

        return 0;
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
