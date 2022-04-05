package com.company.test;

import com.company.stock.StockItem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import com.company.restaurant.Database;
import com.company.ui.UiUtils;
import org.json.JSONObject;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StockItemTest {


    @Test
    void getName() {
        String name = "test";
        int count = 2;
        String expiry ="2022-07-14";
        JSONObject newStockItem = new JSONObject();
        newStockItem.put("name", name);
        newStockItem.put("count", count);
        newStockItem.put("expiry_date", expiry);
        newStockItem.put("allergens", "None");
        newStockItem.put("isFood", true);
        int id = Database.writeToTable("stockitems", newStockItem);
        ArrayList<String> cols = new ArrayList<>();
        cols.add("name");
        cols.add("count");
        cols.add("expiry_date");
        cols.add("allergens");
        cols.add("isFood");
        cols.add("stock_item_id");

        JSONObject stockItemDetails = Database.readFromTable("stockitems", id, cols, "stock_item_id");
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
        newStockItem.put("count", count);
        newStockItem.put("expiry_date", expiry);
        newStockItem.put("allergens", "None");
        newStockItem.put("isFood", true);

        int id = Database.writeToTable("stockitems", newStockItem);
        ArrayList<String> cols = new ArrayList<>();
        cols.add("name");
        cols.add("count");
        cols.add("expiry_date");
        cols.add("allergens");
        cols.add("isFood");
        cols.add("stock_item_id");

        JSONObject stockItemDetails = Database.readFromTable("stockitems", id, cols, "stock_item_id");
        StockItem item = new StockItem(stockItemDetails);
        assertEquals(count, item.getCount() );
    }
}