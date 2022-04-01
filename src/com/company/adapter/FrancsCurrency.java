package com.company.adapter;

public class FrancsCurrency implements EuropeanCurrency{

    public void convertPoundsToEuro(double amount) {
        // Do nothing
    }

    public void convertFrancsToEuro(double amount) {
        double newAmount = amount * 0.97;
        System.out.println("Francs to Euro: " + newAmount);

    }
}
