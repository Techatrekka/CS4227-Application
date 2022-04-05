package menu;

import java.time.LocalDate;
import java.util.ArrayList;

public class SpecialMenu extends Menu {
    protected double discount;

    public SpecialMenu(int id, String name, String description, LocalDate date_created, double discount, String menuItems){
        super(id, name, description, date_created, menuItems);
        this.discount = discount;
    }

    public boolean isSpecialMenu(){
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + "\nDiscount on menu: " + this.discount + "\n";
    }
}
