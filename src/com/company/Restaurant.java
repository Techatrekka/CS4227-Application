package com.company;

import java.util.ArrayList;

import com.company.menu.Menu;
import com.company.menu.MenuFactory;

import org.json.JSONArray;
import org.json.JSONObject;

public class Restaurant {
    public ArrayList<Menu> initMenus() {
        JSONArray dbMenus = Database.readAllFromTable("menu", -1, null, "");

        MenuFactory menuFactory = new MenuFactory();
        ArrayList<Menu> menus = new ArrayList<Menu>();


        for (Object obj : dbMenus){
            JSONObject obj2 = (JSONObject)obj;
            Menu menu = menuFactory.createMenu(obj2);
            menus.add(menu);
        }

        for (Object obj : dbMenus){
            JSONObject obj2 = (JSONObject)obj;
             //if(obj2.getInt("menu_id") == (menuID)){
                // System.out.println(obj2.get("name"));
                // System.out.println(obj2.get("description"));
                // JSONArray menuItems = Database.readAllfromTable("menuitem", menuID, "menu_id", "");
                // for (Object obj3 : menuItems){
                //     JSONObject obj4 = (JSONObject)obj3;
                //     if(obj4.getBoolean("food")){
                //         // Read from Food table
                //     }else{
                //         // Read from Beverage table
                //     }
                // }
        }
            return menus;
    }
}
