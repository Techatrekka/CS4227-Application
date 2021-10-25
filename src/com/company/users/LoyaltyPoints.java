package src.com.company.users;

public class LoyaltyPoints{
    private int numOfPoints;
    private int pointsValue;

    public LoyaltyPoints(){
        this.numOfPoints = 0;
    }

    public int getLoyaltyPoints(){
        return this.numOfPoints;
    }

    public int getPointsValue(){
        return this.pointsValue;
    }

}