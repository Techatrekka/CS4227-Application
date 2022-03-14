package com.company.stock;

import org.json.JSONObject;

import java.time.LocalDate;

public class StockItem extends StockComponent {
    private String name;
    private int count;
    private LocalDate expiryDate;
    private boolean isFood;

    public StockItem(JSONObject details) {
        this.name = details.getString("name");
        this.count = details.getInt("count");
        this.expiryDate = LocalDate.parse(details.getString("expiry_date"));
        this.isFood = details.getBoolean("isFood");
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return  count;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String show() {
        return "\n" + getName() +
                "\n--" + getCount() + "\n" +
                "-- " + getExpiryDate();
    }
}
