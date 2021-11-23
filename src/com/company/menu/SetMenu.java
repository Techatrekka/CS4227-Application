package com.company.menu;

import java.time.LocalDate;
import java.util.ArrayList;

public class SetMenu extends Menu {
    protected double setMenuPrice;

    public SetMenu(int id, String name, String description, LocalDate date, double setMenuPrice) {
        super(id, name, description, date);
        this.setMenuPrice = setMenuPrice;
        super.menuList = new ArrayList<>();
    }
}
