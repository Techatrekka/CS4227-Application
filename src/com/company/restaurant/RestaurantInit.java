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
            // obj2 contains menu_items string - split string on commas to get item ids
            // for every num, read menu_items table and get JSONObject for item
            // create dish or beverage depending on which it is
            // Need to update menu, beverage, dish, menu item classes to take into account new JSON data
        }
        return menus;
    }
}
