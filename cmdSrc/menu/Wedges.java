package menu;

public class Wedges extends MenuItemDecorator {
    private MenuItem item;

    public Wedges(MenuItem item){
        this.item = item;
    }

    public int getID() {
        return item.id;
    }

    public String toString() {
        item.description += " + Wedges";
        return item.toString();
    }
    public String getDescription() {
        if(super.getDescription() == null) {
            return decorateWithWedges();
        } else {
            return super.getDescription() + decorateWithWedges();
        }
    }
    private String decorateWithWedges() {
        return " + Wedges";
    }
    public double getPrice(){
        return item.getPrice() + 3.45;
    }
}
