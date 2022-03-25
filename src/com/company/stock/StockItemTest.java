package com.company.stock;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import com.company.restaurant.Database;
import com.company.ui.UiUtils;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

class StockItemTest {
    JSONObject newStockItem = new JSONObject();
    String name = "test name";
    int count = 2;
    String expiry ="2022-07-14";
    @Test
    void getName() {
        newStockItem.put("name", name);
        newStockItem.put("count", count);
        newStockItem.put("expiry_date", expiry);
        Database.writeToTable("stockitems", newStockItem);
        JSONArray returned = Database.readAllFromTable("stockitems", -1, null, "");
        StockItem item = new StockItem(returned.getJSONObject(0));
        assertEquals(name, item.getName() );
    }

    @Test
    void getCount() {
        newStockItem.put("name", name);
        newStockItem.put("count", count);
        newStockItem.put("expiry_date", expiry);
        Database.writeToTable("stockitems", newStockItem);
        JSONArray returned = Database.readAllFromTable("stockitems", -1, null, "");
        StockItem item = new StockItem(returned.getJSONObject(returned.length()-1));
        assertEquals(count, item.getCount() );
    }
}