package com.company;

import com.company.ui.RestaurantTerminal;

public class Main {

    public static void main(String[] args) {
        RestaurantTerminal restaurantTerminal = RestaurantTerminal.getInstance();
        restaurantTerminal.run();
    }
}
