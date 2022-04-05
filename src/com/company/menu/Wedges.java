package com.company.menu;

public class Wedges extends MenuItemDecorator {
    private MenuItem item;

    public Wedges(MenuItem item){
        this.item = item;
    }

    @Override
    public int getID() {
        return item.id;
    }

    @Override
    public String toString() {
        item.description += " + Wedges";
        return item.toString();
    }

    @Override
    public String getDescription() {
        if(super.getDescription() == null) {
            return decorateWithWedges();
        } else {
            return super.getDescription() + decorateWithWedges();
        }
    }

    private String decorateWithWedges() {
        String wedges = " + Wedges";
        return wedges;
    }

    @Override
    public double getPrice(){
        return item.getPrice() + 3.45;
    }
}
