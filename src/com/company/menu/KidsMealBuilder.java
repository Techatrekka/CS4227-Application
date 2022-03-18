package com.company.menu;

public class KidsMealBuilder extends SetMealBuilder {
    public void buildDrink() {
        super.menuItemDetails.put("menu_item", 5);
        super.menuItemDetails.put("name", "Ribena");
        super.menuItemDetails.put("Price", 2.80);
        super.menuItemDetails.put("Description", "Refreshing juice");
        super.menuItemDetails.put("Allergens", "None");
        super.menuItemDetails.put("Ingredients", "3");
        super.menuItemDetails.put("Alcoholic", false);
        super.menuItemDetails.put("isFood", false);
        super.meal.beverage = new Beverage(super.menuItemDetails);
    }
    public void buildMain() {
        super.menuItemDetails.put("menu_item", 15);
        super.menuItemDetails.put("name", "Pepperoni Pizza");
        super.menuItemDetails.put("Price", 5.50);
        super.menuItemDetails.put("Description", "A lovely Italian dish");
        super.menuItemDetails.put("Allergens", "Gluten");
        super.menuItemDetails.put("Ingredients", "19");
        super.menuItemDetails.put("isFood", true);
        super.menuItemDetails.put("Alcoholic", false);
        super.meal.dish = new Dish(super.menuItemDetails);
    }
    public SetMeal getMeal() {
        return meal;
    }
}
