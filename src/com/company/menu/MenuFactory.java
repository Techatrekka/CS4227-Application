package com.company.menu;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Objects;

public class MenuFactory {
    public MenuFactory() {}

    public Menu createMenu(JSONObject menu) {
        // 0.0 will need ""
        if(Objects.equals(menu.getString("discount"), "0.0")) {
            if(Objects.equals(menu.getString("set_menu_price"), "0.0")){
                return new Menu(menu.getInt("menu_id"), menu.getString("name"), menu.getString("description"), LocalDate.parse(menu.getString("date_created")));       
            }else{
                return new SetMenu(menu.getInt("menu_id"), menu.getString("name"), menu.getString("description"), LocalDate.parse(menu.getString("date_created")), Double.valueOf(menu.getString("set_menu_price")));
            }
        } else {
            return new SpecialMenu(menu.getInt("menu_id"), menu.getString("name"), menu.getString("description"), LocalDate.parse(menu.getString("date_created")), Double.valueOf(menu.getString("discount")));
        }
    }
}
