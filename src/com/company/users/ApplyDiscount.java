package com.company.users;

public class ApplyDiscount implements LoyaltyStrategy{
    private LoyaltyPoints loyaltyPoints;

    @Override
    public double applyLoyaltyDiscount(int userID, double price){
        return loyaltyPoints.getLoyaltyPoints(userID)*10;
    }
}

