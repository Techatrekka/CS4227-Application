package com.company.test;

import com.company.menu.dish;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class dishTest {
    String name = "test name";
    double price = 10.50;
    int ID = 0001;
    dish testDish = new dish();

    @Test
    public void testName(){
        testDish.setName(name);
        assertEquals(testDish.getName(), name);
    }
    @Test
    public void testPrice(){
        testDish.setPrice(price);
        assertEquals(price, testDish.getPrice());
    }
    // @Test
    // public void testID(){
    //     testDish.setMenuID(ID);
    //     Assert.assertEquals(ID, testDish.getID());
    // }
}