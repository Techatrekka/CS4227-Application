package com.company.restaurant;

import java.util.ArrayList;

import com.company.menu.Menu;
import com.company.menu.MenuItem;
import com.company.menu.MenuFactory;
import com.company.menu.Beverage;
import com.company.menu.Dish;

import org.json.JSONArray;
import org.json.JSONObject;

public class RestaurantInit {
    public static ArrayList<Menu> initMenus() {
        JSONArray dbMenus = Database.readAllFromTable("menu", -1, null, "");
        JSONArray dbMenuItems = Database.readAllFromTable("menuitem", -1, null, "");
        JSONArray dbDishes = Database.readAllFromTable("dishes", -1, null, "");
        JSONArray dbBeverages = Database.readAllFromTable("beverages", -1, null, "");

        MenuFactory menuFactory = new MenuFactory();
        ArrayList<Menu> menus = new ArrayList<>();
        ArrayList<MenuItem> menuItems = new ArrayList<>();

        for (Object obj : dbMenus){
            JSONObject obj2 = (JSONObject)obj;
            Menu menu = menuFactory.createMenu(obj2);
            menus.add(menu);
        }
        
        for (Object obj : dbMenuItems) {
            JSONObject obj2 = (JSONObject)obj;
            for(Menu menu : menus) {
                if(obj2.getInt("menu_id") == menu.getId()) {
                    if(obj2.getBoolean("food")) {
                        for(Object obj3 : dbDishes) {
                            JSONObject obj4 = (JSONObject) obj3;
                            if(obj2.getInt("dish_bev_id") == obj4.getInt("dish_id")) {
                                MenuItem dish = new Dish(obj4);
                                menu.menuList.add(dish);
                            }
                        }
                    } else {
                        for(Object obj3 : dbBeverages) {
                            JSONObject obj4 = (JSONObject) obj3;
                            if(obj2.getInt("dish_bev_id") == obj4.getInt("beverage_id")) {
                                MenuItem beverage = new Beverage(obj4);
                                menu.menuList.add(beverage);
                            }
                        }
                    }
                }
            }
        }
        return menus;
    }
}
