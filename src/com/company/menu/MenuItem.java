package com.company.menu;

public abstract class MenuItem{
    public String name;
    public double price;
    public int id;
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
        return  "\nId: " + this.getID() + "\nName: " + name +
                "\nPrice: " + price;
    }
}
