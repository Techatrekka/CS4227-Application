package menu;

import org.json.JSONObject;

// Builder
public abstract class SetMealBuilder {
    JSONObject menuItemDetails = new JSONObject();
    protected SetMeal meal = new SetMeal();
    public abstract void buildDrink();
    public abstract void buildMain();
    public abstract SetMeal getMeal();
}