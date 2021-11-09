package com.company.menu;

public abstract class menuItemDecorator extends MenuItem{
    protected MenuItem decoratedMenuItem;

    public menuItemDecorator(MenuItem decoratedMenuItem){
        this.decoratedMenuItem =decoratedMenuItem;
    }
    public String getName() { return decoratedMenuItem.getName(); }
}
