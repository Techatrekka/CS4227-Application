package com.company.menu;

public abstract class MenuItem{
    public String name;
    public double price;
    public int id;
    String description;
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

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }
    @Override
    public String toString(){
        return  "\n\tId: " + this.getID() + "\n\tName: " + name +
                "\n\tPrice: €" + price;
    }
}
