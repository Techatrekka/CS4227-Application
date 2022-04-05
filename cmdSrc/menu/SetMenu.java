package menu;

import java.time.LocalDate;
import java.util.ArrayList;

public class SetMenu extends Menu {
    protected double setMenuPrice;

    public SetMenu(int id, String name, String description, LocalDate date, double setMenuPrice, String menuItems) {
        super(id, name, description, date, menuItems);
        this.setMenuPrice = setMenuPrice;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSet Menu Price: " + this.setMenuPrice + "\n";
    }
}
