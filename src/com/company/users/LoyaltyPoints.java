package com.company.users;

public class LoyaltyPoints{
    private int numOfPoints;
    private int pointsValue;

    public LoyaltyPoints(int points){
        this.numOfPoints = points;
    }

    public int getLoyaltyPoints(){
        return this.numOfPoints;
    }

    public int getPointsValue(){
        this.pointsValue = this.numOfPoints / 10;
        return this.pointsValue;
    }

}