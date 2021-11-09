package com.company.menu;

public class largeMenuItem extends menuItemDecorator{
    public largeMenuItem(MenuItem item){
        super(item);
    }
    public String getName(){
        return "large " + super.getName();
    }
    public double getPrice(){
        return super.getPrice()*1.2;
    }
}
