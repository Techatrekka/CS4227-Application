package com.company.menu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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
    public MenuItem addNewMenuItem(int choice) {
        System.out.println("What do you want to call this menu item?");
        String name = scanner.nextLine();
        System.out.println("How much does this Menu Item cost?");
        String cost = scanner.nextLine();
        Double price = getValidNum(cost);
        if(choice ==  1){
            System.out.println("Is this an alcoholic drink? 1. Yes 2. No");
            String alco = scanner.nextLine();

            while(!isValid(alco, 1, 2)) {
                System.out.println("Please enter a valid number.");
                alco = scanner.nextLine();
            }
            int alcoholic = Integer.parseInt(alco);

            boolean isAlcoholic = (alcoholic == 1) ? true : false;
            MenuItem beverage = new Beverage(name, price, isAlcoholic);
            return beverage;
        }
        else{
            System.out.println("Description of the dish:");
            String desc = scanner.nextLine();
            System.out.println("Does this dish contain any allergens? Please enter each allergen separated by a comma.");
            String allergens = scanner.nextLine();
            String[] allergenList = allergens.split(",");
            MenuItem dish = new Dish(name, price, desc, Arrays.asList(allergenList));
            return dish;
        }
    }

    private boolean isValid(String choice, int min, int max) {
        try {
            int numChoice = Integer.parseInt(choice);
            if(numChoice > max || numChoice < min) {
                return false;
            }
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    private double getValidNum(String cost){
        boolean isValidNum = false;
        double price = 0.0;
        while(!isValidNum) {
            try {
                price = Double.parseDouble(cost);
                isValidNum = true;
            } catch(NumberFormatException e) {
                cost = scanner.nextLine();
            }
        }
        return price;
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
