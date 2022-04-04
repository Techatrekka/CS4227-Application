package com.company.adapter;

public class FrancsCurrency implements EuropeanCurrency{

    public void convertPoundsToEuro(double amount, double rate) {
        // Do nothing
    }

    public void convertFrancsToEuro(double amount, double rate) {
        double newAmount = amount * rate;
        System.out.println("Francs to Euro: " + newAmount);

    }
}
