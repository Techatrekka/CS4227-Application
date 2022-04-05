package com.company.users;

import com.company.restaurant.Database;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoyaltyPoints{
    private int numOfPoints;
    private int pointsValue;

    public LoyaltyPoints(int points){
        this.numOfPoints = points;
    }

    public int getLoyaltyPoints(int userId){
        ArrayList<String> cols = new ArrayList<>();
        cols.add("loyalty_points");
        JSONObject loyaltyPointDetails = Database.readFromTable("loyalty", userId, cols, "user_id");
        numOfPoints = loyaltyPointDetails.getInt("loyalty_points");
        return this.numOfPoints;
    }

    public String getPointsValue(){
        this.pointsValue = this.numOfPoints * 10;
        return this.pointsValue + " cents";
    }

}