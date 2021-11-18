package com.company.menu;

public class LargeItemDecorator extends MenuItemDecorator {
    public LargeItemDecorator(MenuItem item){
        super(item);
    }
    public String getName(){
        return "large " + super.getName();
    }
    public double getPrice(){
        return super.getPrice()*1.2;
    }
}
