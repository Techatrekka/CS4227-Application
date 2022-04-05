package order;

import java.util.ArrayList;

public class ShoppingCart {
    public ArrayList<Visitable> items;

    public ShoppingCart() {
        items = new ArrayList<>();
    }

    public double accept() {
        DeliveryVisitor visitor = new DeliveryVisitor();

        for (Visitable item: items){
            item.accept(visitor);
        }

        return visitor.getTotalDeliveryCost();
    }

}
