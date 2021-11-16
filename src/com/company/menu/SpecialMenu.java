package com.company.menu;

import java.time.LocalDate;
import java.util.ArrayList;

public class SpecialMenu extends Menu {
    double discount = 0.0;

    public SpecialMenu(int id, String name, String description, LocalDate date_created, double discount){
        super(id, name, description, date_created);
        this.discount = discount;
        super.menuList = new ArrayList<>();
    }

    public boolean isSpecialMenu(){
        return true;
    }

}
