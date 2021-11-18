package com.company.menu;

public class SetMeal {
    Dish dish;
    Beverage beverage;

    public String toString() {
        return "Your meal contains " + dish + beverage;
    }

    public double getMealPrice() {
        return dish.getPrice() + beverage.getPrice();
    }
}
