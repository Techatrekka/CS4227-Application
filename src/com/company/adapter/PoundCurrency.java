package com.company.adapter;

public class PoundCurrency implements EuropeanCurrency {
    public void convertPoundsToEuro(double amount) {
        double newAmount = amount * 1.19;
        System.out.println("Pounds to Euro: " + newAmount);
    }

    public void convertFrancsToEuro(double amount) {
        // Do Nothing
    }

}
