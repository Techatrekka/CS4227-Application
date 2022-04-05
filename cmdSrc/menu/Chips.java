package menu;

public class Chips extends MenuItemDecorator {
    private MenuItem item;

    public Chips(MenuItem item){
        this.item = item;
    }
    public String getDescription(){
        return decorateWithChips();
    }
    private String decorateWithChips() {
        return " + Chips";
    }
    public double getPrice(){
        return item.getPrice() + 2.85;
    }
    public int getID() {
        return item.getID();
    }
    public String toString() {
        return "\n\tId: " + item.getID() + "\n\tName: " + item.getName() + "\n\tPrice: " + item.getPrice() +
                "\n\tDescription: " + item.getDescription() + this.getDescription() + "\n\tAllergens: " +
                item.getAllergens();
    }
}
