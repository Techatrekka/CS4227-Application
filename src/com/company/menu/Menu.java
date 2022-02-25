package com.company.menu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import com.company.restaurant.Database;

import org.json.JSONObject;

public class Menu {
    private int menuID;
    private String name;
    private String description;
    private LocalDate dateCreated;
    private Scanner scanner = new Scanner(System.in);
    public ArrayList<MenuItem> menuList;

    public Menu(int id, String name,String description, LocalDate date, String menuItems){
        this.menuID = id;
        this.name = name;
        this.description = description;
        this.dateCreated = date;
        menuList = new ArrayList<>();

        ArrayList<String> cols = new ArrayList<>();
        cols.add("menu_item");
        cols.add("Alcoholic");
        cols.add("Description");
        cols.add("Ingredients");
        cols.add("Price");
        cols.add("isFood");
        cols.add("name");
        String[] items = menuItems.split(",");
        for(String menuItemId : items) {
            JSONObject itemDetails = Database.readFromTable("menuitem", Integer.parseInt(menuItemId), cols, "menu_item", -1, "");
            if(itemDetails.getBoolean("isFood")) {
                menuList.add(new Dish(itemDetails));
            } else {
                menuList.add(new Beverage(itemDetails));
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
            System.out.println(menuItem.getName());
        }
    }
    public MenuItem addNewMenuItem(String choice) {
        System.out.println("What do you want to call this menu item?");
        String name = scanner.nextLine();
        System.out.println("How much does this menu item cost?");
        String cost = scanner.nextLine();
        System.out.println("Describe this menu item:");
        String desc = scanner.nextLine();
        JSONObject newMenuItem = new JSONObject();
        newMenuItem.put("name", name);
        newMenuItem.put("price", cost);
        newMenuItem.put("description", desc);

        if(choice.equalsIgnoreCase("b")){
            System.out.println("Is this an alcoholic drink? y/n");
            String alco = scanner.nextLine();

            while(!alco.equalsIgnoreCase("y") && !alco.equalsIgnoreCase("n")) {
                System.out.println("Please enter a valid option.");
                alco = scanner.nextLine();
            }

            boolean isAlcoholic = alco.equals("y");
            newMenuItem.put("alcoholic", isAlcoholic);
            int id = Database.writeToTable("beverages", newMenuItem);
            newMenuItem.put("beverage_id", id);
            JSONObject newItem = new JSONObject();
            newItem.put("dish_bev_id", id);
            newItem.put("food", false);
            newItem.put("menu_id", this.getId());
            Database.writeToTable("menuitem", newItem);
            MenuItem beverage = new Beverage(newMenuItem);
            menuList.add(beverage);
            return beverage;
        }
        else{
            System.out.println("Does this dish contain any allergens? Please enter each allergen separated by a comma.");
            String allergens = scanner.nextLine();
            newMenuItem.put("allergens", allergens);
            int id = Database.writeToTable("dishes", newMenuItem);
            newMenuItem.put("dish_id", id);
            JSONObject newItem = new JSONObject();
            newItem.put("dish_bev_id", id);
            newItem.put("food", true);
            newItem.put("menu_id", this.getId());
            Database.writeToTable("menuitem", newItem);
            MenuItem dish = new Dish(newMenuItem);
            System.out.println("DISH IS " + dish);
            menuList.add(dish);
            System.out.println("MENU LIST IS " + menuList);
            return dish;
        }
    }

    public boolean removeMenuItem() {
        if(this.menuList.size() > 0) {
            System.out.println(this.getMenuItems());
            System.out.println("Enter the id of the item you'd like to remove from the menu:");
            String choice = scanner.nextLine();
            int id = Integer.parseInt(choice);
            ArrayList<String> cols = new ArrayList<>();
            cols.add("menu_item");
            JSONObject menuItemDetails = Database.readFromTable("menuitem", id, cols, "dish_bev_id", this.getId(), "menu_id");
            if(Database.deleteFromTable("menuitem", cols.get(0), menuItemDetails.getInt("menu_item"))) {
                menuList.removeIf(menuItem -> id == menuItem.getID());
                return true;
            }
        } else {
            System.out.println("This menu has no items in it.");
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder items = new StringBuilder();
        if(menuList != null && menuList.size() > 0) {
            for(MenuItem item : menuList) {
                items.append(item.toString());
           }
        } else {
            items.append("\tNone\n");
        }
        return  "Menu ID: " + menuID +
                " Menu name: " + name +
                " Menu Description: " + description +
                " Date Created: " + dateCreated.toString() +
                "\nMenu Items: \n" + items;
    }

}
