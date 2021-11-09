package com.company.menu;

public class smallItemDecorator extends menuItemDecorator{
    public smallItemDecorator(MenuItem item){
        super(item);
    }
    public String getName(){
        return "Small " + super.getName();
    }
    public double getPrice(){
        return super.getPrice()*0.8;
    }
}
