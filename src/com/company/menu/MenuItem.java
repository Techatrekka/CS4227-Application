package com.company.menu;

public abstract class MenuItem{
    protected String name;
    protected double price;
    protected int id;
    //public ArrayList<>

    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getID(){
        return id;
    }
    public void setID(int id){
        this.id = id;
    }
    @Override
    public String toString(){
        return  "\n\tId: " + this.getID() + "\n\tName: " + name +
                "\n\tPrice: €" + price;
    }
}
