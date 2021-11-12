package com.company.menu;

import java.time.LocalDate;
import java.util.ArrayList;

public class SetMenu extends Menu {

    public SetMenu(String name, String description, LocalDate date) {
        super(name, description, date);
    }

    @Override
    public int getMenuID() {
        return menuID;
    }

    @Override
    public void setMenuID(int id) {
        this.menuID = id;
    }

    @Override
    public String getName() {
        return name;

    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ArrayList<MenuItem> getMenuItems() {
        return menuList;
    }

    @Override
    public void setMenuItems(ArrayList<MenuItem> items) {
        this.menuList = items;

    }


}
