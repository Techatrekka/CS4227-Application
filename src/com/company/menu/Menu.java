package com.company.menu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import com.company.restaurant.Database;

import com.company.stock.StockComponent;
import com.company.ui.UiUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Menu {
    private int menuID;
    private String name;
    private String description;
    private LocalDate dateCreated;
    private Scanner scanner = new Scanner(System.in);
    public ArrayList<MenuItem> menuList;
    private StockComponent stock;
    private String menuItemStr = "menu_item";
    private String alcStr = "Alcoholic";
    private String allergensStr = "Allergens";
    private String isFoodStr = "isFood";
    private String menuItemKeyStr = "menuitem";

    public Menu(int id, String name,String description, LocalDate date, String menuItems){
        this.menuID = id;
        this.name = name;
        this.description = description;
        this.dateCreated = date;
        menuList = new ArrayList<>();

        ArrayList<String> cols = new ArrayList<>();
        cols.add(menuItemStr);
        cols.add(alcStr);
        cols.add("Description");
        cols.add("Ingredients");
        cols.add("Price");
        cols.add(allergensStr);
        cols.add(isFoodStr);
        cols.add("name");

        String[] items = menuItems.split(",");
        if(!items[0].equals("") && !items[0].equals("-1")) {
            for (String menuItemId : items) {
                JSONObject itemDetails = Database.readFromTable(menuItemKeyStr, Integer.parseInt(menuItemId), cols, menuItemStr);
                if (itemDetails.getBoolean(isFoodStr)) {
                    menuList.add(new Dish(itemDetails));
                } else {
                    menuList.add(new Beverage(itemDetails));
                }
            }
        }
    }

    public int getId(){
        return this.menuID;

    }
    public LocalDate getDate(){
        return this.dateCreated;
    }
    public void setId(int id){
        this.menuID = id;
    }
    public String getName(){
        return name;

    }
    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public ArrayList<MenuItem> getMenuItems(){
        return menuList;

    }
    public void setMenuItems(ArrayList<MenuItem> items){
        this.menuList = items;
    }

    public void printMenu(){
        for (MenuItem menuItem : menuList) {
            System.out.println(menuItem.toString());
        }
    }
    public void addNewMenuItem(String choice) {
        System.out.println("What do you want to call this menu item?");
        String newMenuItemName = scanner.nextLine();
        System.out.println("How much does this menu item cost?");
        String cost = scanner.nextLine();
        System.out.println("Describe this menu item:");
        String desc = scanner.nextLine();
        JSONObject newMenuItem = new JSONObject();
        newMenuItem.put("name", newMenuItemName);
        newMenuItem.put("Price", cost);
        newMenuItem.put("Description", desc);
        System.out.println("Does this menu item contain any allergens? Please enter each allergen separated by a comma.\n" +
                "If there are no allergens, press enter or type None");
        String allergens = scanner.nextLine();
        if(allergens.isEmpty()) {
            newMenuItem.put(allergensStr, "None");
        } else {
            newMenuItem.put(allergensStr, allergens);
        }
        System.out.println("What stock items does this menu item use? Please enter the id for each stock item separated by a comma. " +
                "Press enter to see the stock item ids.");
        scanner.nextLine();
        System.out.println(stock.show());
        String ingredients = scanner.nextLine();
        newMenuItem.put("Ingredients", ingredients);
        createNewItem(choice, newMenuItem);
    }

    private void createNewItem(String choice, JSONObject newMenuItem) {
        MenuItem item;
        int id;

        if(choice.equalsIgnoreCase("b")){
            newMenuItem.put(isFoodStr, false);
            System.out.println("Is this an alcoholic drink? y/n");
            String alco = UiUtils.getInputChoice(new ArrayList<String>() {
                {
                    add("y");
                    add("n");
                }
            });

            boolean isAlcoholic = alco.equals("y");
            newMenuItem.put(alcStr, isAlcoholic);
            id = Database.writeToTable(menuItemKeyStr, newMenuItem);
            newMenuItem.put(menuItemStr, id);
            item = new Beverage(newMenuItem);
        } else {
            newMenuItem.put(alcStr, false);
            newMenuItem.put(isFoodStr, true);
            id = Database.writeToTable(menuItemKeyStr, newMenuItem);
            newMenuItem.put(menuItemStr, id);
            item = new Dish(newMenuItem);
        }
        updateMenu(item, "add");
    }

    private void updateMenu(MenuItem item, String action) {
        StringBuilder items = new StringBuilder();
        if(!menuList.isEmpty()) {
            for(int i = 0; i < menuList.size(); i++) {
                if(i == menuList.size()-1) {
                    items.append(menuList.get(i).getID());
                } else {
                    items.append(menuList.get(i).getID()).append(",");
                }
            }
            if(action.equals("add")) items.append(item.getID());
        } else {
            if(action.equals("add")) {
                items = new StringBuilder(String.valueOf(item.getID()));
            } else {
                return;
            }
        }
        JSONObject menuDetails = new JSONObject();
        menuDetails.put("menu_id", getId());
        menuDetails.put("menu_items", items.toString());
        Database.updateTable("menu", menuDetails);
        if(action.equals("add")) menuList.add(item);
    }
    public void addExistingMenuItem() {
        MenuItem item;
        JSONArray dbMenuItems = Database.readAllFromTable(menuItemKeyStr, -1, null, "");
        for (Object obj : dbMenuItems) {
            JSONObject obj2 = (JSONObject) obj;
            item = obj2.getBoolean(isFoodStr) ? new Dish(obj2) : new Beverage(obj2);
            System.out.println(item);
        }

        System.out.println("Enter the id of the item you'd like to add to the menu");
        String choice = scanner.nextLine();
        while(!UiUtils.isValid(choice, -1, -1)) {
            choice = scanner.nextLine();
        }

        for (Object obj : dbMenuItems) {
            JSONObject obj2 = (JSONObject) obj;
            if(obj2.getInt(menuItemStr) == Integer.parseInt(choice)) {
                item = obj2.getBoolean(isFoodStr) ? new Dish(obj2) : new Beverage(obj2);
                updateMenu(item, "add");
                break;
            }
        }
    }

    public void editMenu(StockComponent stock){
        this.stock = stock;
        String newName = askAboutAttr("name");
        String newDes = askAboutAttr("description");

        JSONObject editedMenu = new JSONObject();

        if(!Objects.equals(newName, "")){
            editedMenu.put("name", newName);
            this.setName(newName);
        }
        if(!Objects.equals(newDes, "")){
            editedMenu.put("description", newDes);
            this.setDescription(newDes);
        }

        if(!Objects.equals(newName, "") || !Objects.equals(newDes, "")){
            editedMenu.put("menu_id", this.getId());

            if (Database.updateTable("menu", editedMenu)){
                System.out.println("Menu edited successfully");
            } else{
                System.out.println("Menu was not edited");
            }
        }
        System.out.println("Continue editing the menu to add/remove menu items or go back to home screen? B = back, Any other key = continue");
        String choice = scanner.nextLine();
        if(UiUtils.inputB(choice)) return;
        this.editMenuItems();
    }

    public void editMenuItems() {
        System.out.println("Do you want to add or remove menu items? A = add, R = remove");
        String choice2 = UiUtils.getInputChoice(new ArrayList<String>() {
            {
                add("r");
                add("a");
            }
        });
        if(choice2.equals("a")) {
            System.out.println("Do you want to create a new menu item or choose from existing menu items? N = new, E = existing");
            choice2 = UiUtils.getInputChoice(new ArrayList<String>() {
                {
                    add("n");
                    add("e");
                }
            });
            if(choice2.equals("n")) {
                System.out.println("Would you like to create a new beverage or dish? B = Beverage, D = Dish");
                choice2 = UiUtils.getInputChoice(new ArrayList<String>() {
                    {
                        add("b");
                        add("d");
                    }
                });
                addNewMenuItem(choice2);
            } else {
                addExistingMenuItem();
            }
        } else {
            removeMenuItem();
        }
    }

    public boolean removeMenuItem() {
        if(!this.menuList.isEmpty()) {
            System.out.println(this.getMenuItems());
            System.out.println("Enter the id of the item you'd like to remove from the menu:");
            String choice = scanner.nextLine();
            int id = Integer.parseInt(choice);
            menuList.removeIf(menuItem -> id == menuItem.getID());
            return true;
        } else {
            System.out.println("This menu has no items in it.");
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder items = new StringBuilder();
        boolean noItems = true;
        if(menuList != null && !menuList.isEmpty()) {
            for(MenuItem item : menuList) {
                if(item.ingredientsInStock() && !Objects.equals(item.toString(), "")) {
                    items.append(item);
                    noItems = false;
                }
           }
        } else {
            items.append("\tNone.\n");
        }
        if(noItems) {
            items.append("\tAll menu items are out of stock\n");
        }
        return  "Menu ID: " + menuID +
                " Menu name: " + name +
                " Menu Description: " + description +
                " Date Created: " + dateCreated.toString() +
                "\nMenu Items: \n" + items;
    }

    public String askAboutAttr(String attribute){
        String newAttribute = "";
        System.out.println("Do you want to change the menu " + attribute + "? y/n");
        String input = scanner.nextLine();
        if(input.equalsIgnoreCase("Y")){
            System.out.println("Enter the new menu " + attribute);
            newAttribute = scanner.nextLine();
        }
        return newAttribute;
    }

}
