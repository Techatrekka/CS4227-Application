
package com.company.test;

import com.company.menu.Menu;
import com.company.menu.MenuFactory;
import com.company.restaurant.Database;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.company.menu.Menu;
import com.company.menu.MenuItem;
import com.company.menu.MenuFactory;

import java.time.LocalDate;
import java.util.ArrayList;


class MenuTest {
    String name = "test name";
    double price = 10.50;
    String description = "This is a test";
    JSONObject menuObj = new JSONObject();
    ArrayList<Menu> menus = new ArrayList<Menu>();
    MenuFactory menuFactory = new MenuFactory();

    @Test
    public void testMenu() {
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
        cols.add("menu_id");

        JSONObject menuItemDetails = Database.readFromTable("menu", id, cols, "menu_id");
        Menu menuTest = menuFactory.createMenu(menuItemDetails);
        System.out.println(menuTest);

        System.out.println("test");
        assertEquals(name, menuTest.getName());
        Database.deleteFromTable("menu", "menu_id", menuItemDetails.getInt("menu_id"));
    }
}