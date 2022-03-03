package com.company.menu;

public class KidsMealBuilder extends SetMealBuilder {
    public void buildDrink() {
        super.menuItemDetails.put("menu_item", 5);
        super.menuItemDetails.put("name", "Ribena");
        super.menuItemDetails.put("Price", 3.0);
        super.menuItemDetails.put("Description", "Refreshing juice");
        super.menuItemDetails.put("Allergens", "None");
        super.menuItemDetails.put("Ingredients", "7");
        super.menuItemDetails.put("Alcoholic", false);
        super.meal.beverage = new Beverage(super.menuItemDetails);
    }
    public void buildMain() {
        super.menuItemDetails.put("menu_item", 3);
        super.menuItemDetails.put("name", "Pepperoni Pizza");
        super.menuItemDetails.put("Price", 6.50);
        super.menuItemDetails.put("Description", "A lovely Italian dish");
        super.menuItemDetails.put("Allergens", "Gluten");
        super.menuItemDetails.put("Ingredients", "3,4");
        super.menuItemDetails.put("Alcoholic", false);
        super.meal.dish = new Dish(super.menuItemDetails);
    }
    public SetMeal getMeal() {
        return meal;
    }
}
