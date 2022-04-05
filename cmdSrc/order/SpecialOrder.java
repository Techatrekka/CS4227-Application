package order;

public class SpecialOrder extends Order{

    private double deliveryDiscount;

    public SpecialOrder(double totalCost, double deliveryDiscount){
        super(totalCost);
        this.deliveryDiscount = deliveryDiscount;
    }

    public double getDeliveryDiscount(){
        return this.deliveryDiscount;
    }

    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}
