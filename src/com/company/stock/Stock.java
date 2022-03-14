package com.company.stock;

import java.util.ArrayList;
import java.util.Iterator;

public class Stock extends StockComponent {
    int capacity;
    int currentCapacity;
    ArrayList<StockItem> stockItems;
    private final ArrayList stockComponents = new ArrayList();

    public Stock(int capacity) {
        this.capacity = capacity;
        this.stockItems = new ArrayList<>();
        currentCapacity = 0;
    }

    public int getCapacity() {
        return this.capacity;
    }

    void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void addNewStockItem() {

    }

    public int getCurrentCapacity() {
        return this.currentCapacity;
    }

    public void addStockItem(StockItem item) {
        stockItems.add(item);
        ++currentCapacity;
    }

    public ArrayList<StockItem> getStockItems() {
        return stockItems;
    }

    public void add(StockComponent stockComponent) {
        stockComponents.add(stockComponent);
    }

    public void remove(StockComponent stockComponent) {
        stockItems.remove(stockComponent);
    }

    public StockComponent getChild(int index) {
        return (StockComponent) stockComponents.get(index);
    }

    public String show() {
        String stockDisplay = getStockComponentInfo();
        Iterator iterator = stockComponents.iterator();
        while (iterator.hasNext()) {
            StockComponent stockComponent = (StockComponent) iterator.next();
            stockDisplay += "\n" + stockComponent.show();
        }
        return stockDisplay;
    }

    private String getStockComponentInfo() {
        return getName() +
                "\nTotal Capacity: " + getCapacity() +
                "\nCurrent Capacity: " + getCurrentCapacity();
    }
}
