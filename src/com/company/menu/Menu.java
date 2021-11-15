package com.company.menu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.xml.crypto.Data;

import com.company.Database;

import org.json.JSONObject;

public class Menu {
    ArrayList<MenuItem>menuList;
    int menuID;
    String name;
    String description;
    LocalDate dateCreated;
    Scanner scanner = new Scanner(System.in);

    public Menu(int id, String name,String description, LocalDate date){
        this.menuID = id;
        this.name = name;
        this.description = description;
        this.dateCreated = date;
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
        for(int x=0; x<menuList.size();x++){
            System.out.println(menuList.get(x).getName());
        }
    }
    public MenuItem addNewMenuItem(String choice) {
        System.out.println("What do you want to call this menu item?");
        String name = scanner.nextLine();
        System.out.println("How much does this menu item cost?");
        String cost = scanner.nextLine();
        Double price = Double.valueOf(cost);
        JSONObject newMenuItem = new JSONObject();
        newMenuItem.put("name", name);
        newMenuItem.put("price", cost);

        if(choice.equalsIgnoreCase("b")){
            System.out.println("Is this an alcoholic drink? y/n");
            String alco = scanner.nextLine();

            while(!alco.equalsIgnoreCase("y") && !alco.equalsIgnoreCase("n")) {
                System.out.println("Please enter a valid option.");
                alco = scanner.nextLine();
            }

            boolean isAlcoholic = (alco.equals("y")) ? true : false;
            newMenuItem.put("alcoholic", isAlcoholic);
            int id = Database.writeToTable("beverages", newMenuItem);
            MenuItem beverage = new Beverage(id, name, price, isAlcoholic);
            menuList.add(beverage);
            return beverage;
        }
        else{
            System.out.println("Description of the dish:");
            String desc = scanner.nextLine();
            System.out.println("Does this dish contain any allergens? Please enter each allergen separated by a comma.");
            String allergens = scanner.nextLine();
            String[] allergenList = allergens.split(",");

            newMenuItem.put("description", desc);
            newMenuItem.put("allergens", allergens);
            int id = Database.writeToTable("dishes", newMenuItem);
            JSONObject newItem = new JSONObject();
            newItem.put("dish_bev_id", id);
            newItem.put("food", true);
            newItem.put("menu_id", this.getId());
            Database.writeToTable("menuitem", newItem);
            MenuItem dish = new Dish(id, name, price, desc, Arrays.asList(allergenList));
            menuList.add(dish);
            return dish;
        }
    }

    @Override
    public String toString() {
        StringBuilder items = new StringBuilder();
        // for(MenuItem item : menuList) {
        //     items.append(item.toString()).append("\n");
        // }
        return  "Menu name: " + name +
                " Menu ID: " + menuID +
                " Menu Date: " + dateCreated.toString() +
                " Menu Items: " + items;
    }

}
