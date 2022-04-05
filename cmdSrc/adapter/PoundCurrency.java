package adapter;

public class PoundCurrency implements EuropeanCurrency {
    public void convertPoundsToEuro(double amount, double rate) {
        double newAmount = amount * rate;
        System.out.println("Pounds to Euro: " + newAmount);
    }

    public void convertFrancsToEuro(double amount,double rate) {
        // Do Nothing
    }
}
