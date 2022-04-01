package com.company.order;

public class DeliveryVisitor implements Visitor{

    private double totalDeliveryCost;

    public void visit(Order order){
        totalDeliveryCost = 0.0;
        if(order.getTotalCost() < 10) {
            totalDeliveryCost += order.getMenuItems().size() * 0.4;
        } else if(order.getTotalCost() < 20) {
            totalDeliveryCost += order.getMenuItems().size() * 0.2;
        } else {
            totalDeliveryCost = 0.0;
        }
    }

    public void visit(SpecialOrder order){
        totalDeliveryCost = 0.0;
        if(order.getTotalCost() < 10) {
            totalDeliveryCost += order.getMenuItems().size() * 0.4;
        } else if(order.getTotalCost() < 20) {
            totalDeliveryCost += order.getMenuItems().size() * 0.2;
        } else {
            totalDeliveryCost = 0.0;
        }
        totalDeliveryCost *= (1 - order.getDeliveryDiscount());
    }


    public double getTotalDeliveryCost(){
        return totalDeliveryCost;
    }

}
