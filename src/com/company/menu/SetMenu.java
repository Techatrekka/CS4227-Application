package com.company.menu;

import java.time.LocalDate;


public class SetMenu extends Menu {
    int set_menu_price = 0;

    public SetMenu(int id, String name, String description, LocalDate date, int set_menu_price) {
        super(id, name, description, date);
        this.set_menu_price = set_menu_price;
    }
}
