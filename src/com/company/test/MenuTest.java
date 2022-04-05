
package com.company.test;

import com.company.menu.Menu;
import com.company.menu.MenuFactory;
import com.company.restaurant.Database;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


import java.time.LocalDate;
import java.util.ArrayList;


class MenuTest {
    String name = "test name";
    double price = 10.50;
    String description = "This is a test";
    JSONObject menuObj = new JSONObject();
    MenuFactory menuFactory = new MenuFactory();

    @Test
    public void testMenu() {
        String menuIdStr = "menu_id";
        menuObj.put("set_menu_price", price);
        menuObj.put("name", name);
        menuObj.put("description", description);
        menuObj.put("discount", "0.0");
        menuObj.put("date_created", LocalDate.now());

        int id = Database.writeToTable("menu", menuObj);
        System.out.println(id);
        ArrayList<String> cols = new ArrayList<>();
        cols.add("name");
        cols.add("set_menu_price");
        cols.add("description");
        cols.add("discount");
        cols.add("date_created");
        cols.add(menuIdStr);

        JSONObject menuItemDetails = Database.readFromTable("menu", id, cols, menuIdStr);
        Menu menuTest = menuFactory.createMenu(menuItemDetails);
        System.out.println(menuTest);

        System.out.println("test");
        assertEquals(name, menuTest.getName());
        Database.deleteFromTable("menu", menuIdStr, menuItemDetails.getInt(menuIdStr));
    }
}