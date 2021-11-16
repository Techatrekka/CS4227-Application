package com.company.menu;

import java.time.LocalDate;
import java.util.ArrayList;


public class SetMenu extends Menu {
    double set_menu_price = 0.0;

    public SetMenu(int id, String name, String description, LocalDate date, double set_menu_price) {
        super(id, name, description, date);
        this.set_menu_price = set_menu_price;
        super.menuList = new ArrayList<>();
    }
}
