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

        MenuFactory menuFactory = new MenuFactory();
        ArrayList<Menu> menus = new ArrayList<>();

        for (Object obj : dbMenus) {
            JSONObject obj2 = (JSONObject) obj;
            Menu menu = menuFactory.createMenu(obj2);
            menus.add(menu);
        }
        return menus;
    }
}
