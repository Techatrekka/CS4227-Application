package com.company.order;

public class SpecialOrder extends Order{

    private double discount;

    public SpecialOrder(double totalCost, double discount){
        super(totalCost);
        this.discount = discount;
    }

    public double getDiscount(){
        return this.discount;
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}
