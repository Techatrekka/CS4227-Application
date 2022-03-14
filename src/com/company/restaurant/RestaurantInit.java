package com.company.restaurant;

import java.util.ArrayList;

import com.company.menu.Menu;
import com.company.menu.MenuItem;
import com.company.menu.MenuFactory;
import com.company.menu.Beverage;
import com.company.menu.Dish;

import com.company.stock.Stock;
import com.company.stock.StockItem;
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

    public static Stock initStock() {
        Stock stock = new Stock(1500);
        JSONArray dbStock = Database.readAllFromTable("stockitems", -1, null, "");
        for (Object obj : dbStock) {
            JSONObject obj2 = (JSONObject) obj;
            StockItem item = new StockItem(obj2);
            stock.addStockItem(item);
        }
        return stock;
    }
}
