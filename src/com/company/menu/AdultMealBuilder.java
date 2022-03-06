package com.company.menu;

public class AdultMealBuilder extends SetMealBuilder {
    public void buildDrink() {
        super.menuItemDetails.put("menu_item", 4);
        super.menuItemDetails.put("name", "Peroni");
        super.menuItemDetails.put("Price", 6.20);
        super.menuItemDetails.put("Alcoholic", true);
        super.menuItemDetails.put("Description", "Nice cool beer");
        super.menuItemDetails.put("Ingredients", "5");
        super.menuItemDetails.put("isFood", false);
        super.menuItemDetails.put("Allergens", "None");
        super.meal.beverage = new Beverage(super.menuItemDetails);
    }
    public void buildMain() {
        super.menuItemDetails.put("menu_item", 3);
        super.menuItemDetails.put("name", "Pepperoni Pizza");
        super.menuItemDetails.put("Price", 6.50);
        super.menuItemDetails.put("Alcoholic", false);
        super.menuItemDetails.put("Description", "A lovely Italian dish");
        super.menuItemDetails.put("Ingredients", "3,4");
        super.menuItemDetails.put("isFood", true);
        super.menuItemDetails.put("Allergens", "Gluten");
        super.meal.dish = new Dish(super.menuItemDetails);
    }
    public SetMeal getMeal() {
        return meal;
    }
}
