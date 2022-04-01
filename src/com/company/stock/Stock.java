package com.company.stock;

import com.company.restaurant.Database;
import com.company.ui.UiUtils;
import org.json.JSONObject;

import java.util.*;

public class Stock extends StockComponent {
    int capacity;
    int currentCapacity;
    String type;
    private ArrayList<StockComponent> stockComponents = new ArrayList();
    private Scanner scanner = new Scanner(System.in);

    public Stock(int capacity, String type) {
        this.capacity = capacity;
        this.type = type;
        currentCapacity = 0;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public String show() {
        StringBuilder stockDisplay = new StringBuilder(getStockInfo());
        for (StockComponent stockComponent : stockComponents) {
            stockDisplay.append(stockComponent.show());
        }
        return stockDisplay.toString();
    }

    private String getStockInfo() {
        return "\nTotal Capacity: " + getCapacity() +
                "\nCurrent Capacity: " + getCurrentCapacity();
    }

    public StockComponent getChild(int index) {
        return stockComponents.get(index);
    }

    void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    String checkAtCapacity(int count) {
        while (currentCapacity + count > capacity) {
            System.out.println("Sorry, adding this amount would exceed the stocks capacity. \nTotal Capacity: " + capacity +
                    " Current capacity: " + currentCapacity);
            System.out.println("Enter a a count that won't exceed the total capacity:");
            count = Integer.parseInt(scanner.nextLine());
        }
        return String.valueOf(count);
    }

    public boolean isAtMaxCapacity() {
        return currentCapacity >= capacity;
    }

    public void addNewStockItem() {
        System.out.println("What do you want to call this stock item?");
        String name = scanner.nextLine();
        System.out.println("How much of this item is in stock currently? Enter a whole number - it can be 0.");
        String count = scanner.nextLine();
        count = checkAtCapacity(Integer.parseInt(count));
        System.out.println("When does the stock item expire? Enter the date in this format YYYY-MM-DD");
        String expiry = scanner.nextLine();
        JSONObject newStockItem = new JSONObject();
        newStockItem.put("name", name);
        newStockItem.put("count", count);
        newStockItem.put("expiry_date", expiry);
        System.out.println("Does this stock item contain any allergens? Please enter each allergen separated by a comma.\n" +
                "If there are no allergens, press enter or type None");
        String allergens = scanner.nextLine();
        if(allergens.isEmpty()) {
            newStockItem.put("allergens", "None");
        } else {
            newStockItem.put("allergens", allergens);
        }
        System.out.println("Is this stock item a food? Y = yes, anything else = no");
        String isFood = scanner.nextLine();
        newStockItem.put("isFood", isFood.equalsIgnoreCase("y"));
        Database.writeToTable("stockitems", newStockItem);
    }

    public void updateStockItemDetails() {
        JSONObject updatedStockItem = new JSONObject();
        System.out.println(show());
        System.out.println("Enter the id of the stock item whose details you want to update.");
        int id = Integer.parseInt(scanner.nextLine());
        updatedStockItem.put("stock_item_id", id);

        System.out.println("Do you want to edit the name, count, expiry date or allergens for the item? \n" +
                "N = name, C = count, E = expiry date, A = allergens");
        String choice = UiUtils.getInputChoice(new ArrayList<String>() {
            {
                add("n");
                add("c");
                add("e");
                add("a");
            }
        });
        String change;
        switch (choice) {
            case "n":
                System.out.println("Enter the new name");
                change = scanner.nextLine();
                updatedStockItem.put("name", change);
                break;
            case "c":
                System.out.println("Enter the new count for the item as a whole number");
                change = scanner.nextLine();
                checkAtCapacity(Integer.parseInt(change));
                updatedStockItem.put("count", change);
                break;
            case "e":
                System.out.println("Enter the new expiry date in the form YYYY-MM-DD");
                change = scanner.nextLine();
                updatedStockItem.put("expiry_date", change);
                break;
            case "a":
                System.out.println("Enter the new Allergens list for the stock item." +
                        "Please enter each allergen separated by a comma. If there are no allergens, press enter or type None");
                change = scanner.nextLine();
                if(change.isEmpty()) {
                    updatedStockItem.put("allergens", "None");
                } else {
                    updatedStockItem.put("allergens", change);
                }
                break;
        }
        Database.updateTable("stockitems", updatedStockItem);
    }

    public int getCurrentCapacity() {
        return this.currentCapacity;
    }

    public ArrayList<StockComponent> getStockItems() {
        return stockComponents;
    }

    public void add(StockComponent stockComponent) {
        stockComponents.add(stockComponent);
        currentCapacity += stockComponent.getCount();
    }

    public void remove(StockComponent stockComponent) {
        stockComponents.remove(stockComponent);
    }

    public void removeStockItemsForOrder(HashMap<Integer, Integer> stockItemMap) {
        for(Map.Entry entry : stockItemMap.entrySet()) {
            StockItem s = getStockItemById((Integer) entry.getKey());
            int newCount = s.getCount() - (int) entry.getValue();
            JSONObject stockItemDetails = new JSONObject();
            stockItemDetails.put("stock_item_id", s.getId());
            stockItemDetails.put("count", newCount);
            Database.updateTable("stockitems", stockItemDetails);
        }
    }

    private StockItem getStockItemById(int id) {
        for(StockComponent s : stockComponents) {
            if(((StockItem) s).getId() == id) return (StockItem) s;
        }
        return null;
    }

    public boolean ingredientsInStock(HashMap<Integer, Integer> stockItems) {
        for(Map.Entry<Integer, Integer> entry : stockItems.entrySet()) {
            StockItem s = getStockItemById(entry.getKey());
            if(entry.getValue() > s.getCount()) return false;
        }
        return true;
    }
}
