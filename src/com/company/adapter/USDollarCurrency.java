package com.company.adapter;

public class USDollarCurrency implements OtherCurrency {
    CurrencyAdapter currencyAdapter;

    @Override
    public void convertToEuro(String countryName, double amount, double duty) {
        if (countryName.equalsIgnoreCase("USA")) {
            double newAmount = amount * 0.9;
            newAmount *= duty;
            System.out.println("US Dollar to Euro: " + newAmount);
        } else if (countryName.equalsIgnoreCase("United Kingdom") || countryName.equalsIgnoreCase("Switzerland")) {
            currencyAdapter = new CurrencyAdapter(countryName);
            currencyAdapter.convertToEuro(countryName, amount, duty);
        }else{
            System.out.println("Invalid currency type. " + countryName + " is not supported" );
        }
    }
}
