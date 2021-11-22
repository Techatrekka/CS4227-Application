package com.company.menu;

public class AdultMealBuilder extends SetMealBuilder {
    public void buildDrink() {
        super.menuItemDetails.put("beverage_id", 3);
        super.menuItemDetails.put("name", "Peroni");
        super.menuItemDetails.put("price", 6.20);
        super.menuItemDetails.put("alcoholic", true);
        super.meal.beverage = new Beverage(super.menuItemDetails);
    }
    public void buildMain() {
        super.menuItemDetails.put("dish_id", 3);
        super.menuItemDetails.put("name", "Pizza");
        super.menuItemDetails.put("price", 6.50);
        super.menuItemDetails.put("description", "A lovely Italian dish");
        super.menuItemDetails.put("allergens", "None");
        super.meal.dish = new Dish(super.menuItemDetails);
    }
    public SetMeal getMeal() {
        return meal;
    }
}
