package com.company.menu;

public class SmallItemDecorator extends MenuItemDecorator {
    public SmallItemDecorator(MenuItem item){
        super(item);
    }
    public String getName(){
        return "Small " + super.getName();
    }
    public double getPrice(){
        return super.getPrice()*0.8;
    }
}
