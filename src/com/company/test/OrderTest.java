package com.company.test;

import com.company.menu.*;
import com.company.order.Order;
import com.company.stock.StockItem;
import com.company.users.User;
import com.company.restaurant.Database;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private Object JSONObject;

    @Test
    void getName() {
        Order newOrder = new Order(1);
        MealDirector director = new MealDirector();
        SetMealBuilder builder = new AdultMealBuilder();
        SetMeal meal = director.createMeal(builder);

        for(int id : meal.getMenuItemIds()) {
            MenuItem item = newOrder.addSetMenuItem(id);
            newOrder.addMenuItem(item);
        }
        ArrayList<String> cols = new ArrayList<>();
        cols.add("order_id");
        cols.add("total_cost");
        cols.add("user_id_id");

        JSONObject orderDetails = new JSONObject();
        newOrder.setTotalCost(newOrder.calcCostOfItems());
        orderDetails.put("total_cost", String.valueOf(newOrder.getTotalCost()));
        orderDetails.put("user_id_id", 1);
        int orderId = Database.writeToTable("order", orderDetails);


        JSONObject orderItemDetails = Database.readFromTable("stockitems", orderId, cols, "stock_item_id");
        JSONObject item = new JSONObject(orderItemDetails);
        Order returnedOrder = new Order(item);
        assertEquals(returnedOrder.toString(),newOrder.toString());
    }
}

