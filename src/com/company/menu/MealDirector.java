package com.company.menu;

public class MealDirector {
    public SetMeal createMeal(SetMealBuilder builder) {
        builder.buildDrink();
        builder.buildMain();
        return builder.getMeal();
    }
}
