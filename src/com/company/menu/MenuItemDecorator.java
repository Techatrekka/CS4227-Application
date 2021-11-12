package com.company.menu;

public abstract class MenuItemDecorator extends MenuItem{
    protected MenuItem decoratedMenuItem;

    public MenuItemDecorator(MenuItem decoratedMenuItem){
        this.decoratedMenuItem =decoratedMenuItem;
    }
    public String getName() { return decoratedMenuItem.getName(); }
}
