package com.company.test;

import com.company.stock.StockItem;
import org.json.JSONObject;
import com.company.restaurant.Database;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;


class StockItemTest {
    private String countStr = "count";
    private String expiryStr = "expiry_date";
    private String allergensStr = "allergens";
    private String isFoodStr = "isFood";
    private String stockItemsStr = "stockitems";
    private String stockItemId = "stock_item_id";
    @Test
    void getName() {

        String name = "test";
        int count = 2;
        String expiry ="2022-07-14";
        JSONObject newStockItem = new JSONObject();
        newStockItem.put("name", name);
        newStockItem.put(countStr, count);
        newStockItem.put(expiryStr, expiry);
        newStockItem.put(allergensStr, "None");
        newStockItem.put(isFoodStr, true);
        int id = Database.writeToTable(stockItemsStr, newStockItem);
        ArrayList<String> cols = new ArrayList<>();
        cols.add("name");
        cols.add(countStr);
        cols.add(expiryStr);
        cols.add(allergensStr);
        cols.add(isFoodStr);
        cols.add(stockItemId);

        JSONObject stockItemDetails = Database.readFromTable(stockItemsStr, id, cols, stockItemId);
        StockItem item = new StockItem(stockItemDetails);
        assertEquals(name, item.getName() );
    }
    @Test
    void getCount() {
        String name = "test name";
        int count = 2;
        String expiry ="2022-07-14";
        JSONObject newStockItem = new JSONObject();
        newStockItem.put("name", name);
        newStockItem.put(countStr, count);
        newStockItem.put(expiryStr, expiry);
        newStockItem.put(allergensStr, "None");
        newStockItem.put(isFoodStr, true);

        int id = Database.writeToTable(stockItemsStr, newStockItem);
        ArrayList<String> cols = new ArrayList<>();
        cols.add("name");
        cols.add(countStr);
        cols.add(expiryStr);
        cols.add(allergensStr);
        cols.add(isFoodStr);
        cols.add(stockItemId);

        JSONObject stockItemDetails = Database.readFromTable(stockItemsStr, id,
                cols, stockItemId);
        StockItem item = new StockItem(stockItemDetails);
        assertEquals(count, item.getCount() );
    }
}