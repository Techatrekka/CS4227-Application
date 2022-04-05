package adapter;

public class Currency implements OtherCurrency {
    CurrencyAdapter currencyAdapter;

    @Override
    public void convertToEuro(String countryName, double amount, double duty, double rate) {
        if (countryName.equalsIgnoreCase("United Kingdom") ||
                countryName.equalsIgnoreCase("Switzerland")) {
            currencyAdapter = new CurrencyAdapter(countryName);
            currencyAdapter.convertToEuro(countryName, amount, duty, rate);
        }
        else{
            double newAmount = amount * rate;
            newAmount *= duty;
            System.out.println(countryName + "'s currency to Euro = " + newAmount);
        }
    }
}
