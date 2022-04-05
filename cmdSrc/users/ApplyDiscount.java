package users;

public class ApplyDiscount implements LoyaltyStrategy{
    private LoyaltyPoints loyaltyPoints;

    @Override
    public double applyLoyaltyDiscount(int userID, double price){
        loyaltyPoints = new LoyaltyPoints(0);
        return loyaltyPoints.getLoyaltyPoints(userID)*.01;
    }
}

