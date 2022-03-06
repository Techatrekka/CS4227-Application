package com.company.menu;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Objects;

public class MenuFactory {
    public MenuFactory() {}

    public Menu createMenu(JSONObject menu) {
        if(Objects.equals(menu.getString("discount"), "0.0")) {
            if(Objects.equals(menu.getString("set_menu_price"), "0.0")){
                return new Menu(menu.getInt("menu_id"), menu.getString("name"), menu.getString("description"),
                        LocalDate.parse(menu.getString("date_created")), menu.optString("menu_items", ""));
            }else{
                return new SetMenu(menu.getInt("menu_id"), menu.getString("name"), menu.getString("description"),
                        LocalDate.parse(menu.getString("date_created")), Double.parseDouble(menu.getString("set_menu_price")),
                        menu.optString("menu_items", ""));
            }
        } else {
            return new SpecialMenu(menu.getInt("menu_id"), menu.getString("name"), menu.getString("description"),
                    LocalDate.parse(menu.getString("date_created")), Double.parseDouble(menu.getString("discount")),
                    menu.optString("menu_items", ""));
        }
    }
}
