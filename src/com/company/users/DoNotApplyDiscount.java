package com.company.users;

public class DoNotApplyDiscount implements LoyaltyStrategy{

    @Override
    public double applyLoyaltyDiscount(int userID, double price){
        return 0.0;

    }
}
