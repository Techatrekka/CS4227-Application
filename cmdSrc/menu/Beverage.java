package menu;

import org.json.JSONObject;

import java.util.Arrays;

public class Beverage extends MenuItem {
    private boolean alcoholic;
    public Beverage(JSONObject bevDetails){
        super.id = bevDetails.getInt("menu_item");
        super.name = bevDetails.getString("name");
        super.price = bevDetails.getDouble("Price");
        super.description = bevDetails.getString("Description");
        this.alcoholic = bevDetails.getBoolean("Alcoholic");
        super.allergens = Arrays.asList(bevDetails.getString("Allergens").split(","));
        super.ingredients = Arrays.asList(bevDetails.getString("Ingredients").split(","));
    }
    @Override
    public double getPrice() {
        return super.price;
    }

    @Override
    public void setPrice(double price) {
        super.price = price;

    }

    @Override
    public String getName() {
        return super.name;
    }
    @Override
    public void setName(String name) {
        super.name = name;
    }
    public String toString(){
        if(alcoholic){
            return super.toString()+"\n\tAlcoholic: Yes\n";
        }
        else{
            return super.toString()+"\n\tAlcoholic: No\n";
        }
    }
}
