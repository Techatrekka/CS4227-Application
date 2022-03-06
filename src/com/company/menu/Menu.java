package com.company.menu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import com.company.restaurant.Database;

import com.company.ui.UiUtils;
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
        cols.add("Allergens");
        cols.add("isFood");
        cols.add("name");
        String[] items = menuItems.split(",");
        if(!items[0].equals("") && !items[0].equals("-1")) {
            for (String menuItemId : items) {
                JSONObject itemDetails = Database.readFromTable("menuitem", Integer.parseInt(menuItemId), cols, "menu_item");
                if (itemDetails.getBoolean("isFood")) {
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
            System.out.println(menuItem.getName());
        }
    }
    public void addNewMenuItem(String choice) {
        MenuItem item;

        System.out.println("What do you want to call this menu item?");
        String name = scanner.nextLine();
        System.out.println("How much does this menu item cost?");
        String cost = scanner.nextLine();
        System.out.println("Describe this menu item:");
        String desc = scanner.nextLine();
        JSONObject newMenuItem = new JSONObject();
        newMenuItem.put("name", name);
        newMenuItem.put("Price", cost);
        newMenuItem.put("Description", desc);
        System.out.println("Does this menu item contain any allergens? Please enter each allergen separated by a comma.");
        String allergens = scanner.nextLine();
        newMenuItem.put("Allergens", allergens);
        System.out.println("What stock items does this menu item use? Please enter the id for each stock item separated by a comma.");
        String ingredients = scanner.nextLine();
        newMenuItem.put("Ingredients", ingredients);

        if(choice.equalsIgnoreCase("b")){
            newMenuItem.put("isFood", false);
            System.out.println("Is this an alcoholic drink? y/n");
            String alco = UiUtils.getInputChoice(new ArrayList<String>() {
                {
                    add("y");
                    add("n");
                }
            });

            boolean isAlcoholic = alco.equals("y");
            newMenuItem.put("Alcoholic", isAlcoholic);
            item = new Beverage(newMenuItem);
        } else {
            newMenuItem.put("Alcoholic", false);
            newMenuItem.put("isFood", true);
            item = new Dish(newMenuItem);
        }
        Database.writeToTable("menuitem", newMenuItem);
        menuList.add(item);
    }

    public void addExistingMenuItem() {

    }

    public boolean removeMenuItem() {
        if(this.menuList.size() > 0) {
            System.out.println(this.getMenuItems());
            System.out.println("Enter the id of the item you'd like to remove from the menu:");
            String choice = scanner.nextLine();
            int id = Integer.parseInt(choice);
            if(Database.deleteFromTable("menuitem", "menu_item", id)) {
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
