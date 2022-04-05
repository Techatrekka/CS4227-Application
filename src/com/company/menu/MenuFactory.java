package com.company.menu;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Objects;

public class MenuFactory {
    public MenuFactory() {
        // Empty default constructor
    }

    public Menu createMenu(JSONObject menu) {
        String menuIdStr = "menu_id";
        String desc = "description";
        String dateStr = "date_created";
        String menuItemsStr = "menu_items";
        if(Objects.equals(menu.getString("discount"), "0.0")) {
            if(Objects.equals(menu.getString("set_menu_price"), "0.0")){
                return new Menu(menu.getInt(menuIdStr), menu.getString("name"), menu.getString(desc),
                        LocalDate.parse(menu.getString(dateStr)), menu.optString(menuItemsStr, ""));
            }else{
                return new SetMenu(menu.getInt(menuIdStr), menu.getString("name"), menu.getString(desc),
                        LocalDate.parse(menu.getString(dateStr)), Double.parseDouble(menu.getString("set_menu_price")),
                        menu.optString(menuItemsStr, ""));
            }
        } else {
            return new SpecialMenu(menu.getInt(menuIdStr), menu.getString("name"), menu.getString(desc),
                    LocalDate.parse(menu.getString(dateStr)), Double.parseDouble(menu.getString("discount")),
                    menu.optString(menuItemsStr, ""));
        }
    }
}
