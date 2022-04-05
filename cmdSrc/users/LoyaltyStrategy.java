package users;

public interface LoyaltyStrategy {

    public double applyLoyaltyDiscount(int userID, double price);
}

