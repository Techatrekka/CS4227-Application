package com.company.users;

public class ApplyDiscount implements LoyaltyStrategy{

    @Override
    public double applyLoyaltyDiscount(int userID, double price){
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(0);
        return loyaltyPoints.getLoyaltyPoints(userID)*.01;
    }
}

