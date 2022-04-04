package com.company.order;

import com.company.menu.*;
import com.company.stock.StockItem;
import com.company.users.User;
import com.company.restaurant.Database;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private Object JSONObject;

    @Test
    void getName() {
        Order newOrder = new Order();
        MealDirector director = new MealDirector();
        SetMealBuilder builder = new AdultMealBuilder();
        SetMeal meal = director.createMeal(builder);

        for(int id : meal.getMenuItemIds()) {
            MenuItem item = newOrder.addSetMenuItem(id);
            newOrder.addMenuItem(item);
        }
        JSONObject orderDetails = new JSONObject();
        newOrder.setTotalCost(newOrder.calcCostOfItems());
        orderDetails.put("total_cost", String.valueOf(newOrder.getTotalCost()));
        orderDetails.put("user_id", 1);
        int orderId = Database.writeToTable("order", orderDetails);

        JSONArray allUserOrders = Database.readAllFromTable("order", 1, "user_id", "");
        JSONObject item = new JSONObject(allUserOrders.getJSONObject(0));
        Order returnedOrder = new Order(item);
        assertEquals(returnedOrder.toString(),newOrder.toString());
    }
}