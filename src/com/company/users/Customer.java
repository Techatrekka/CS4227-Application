package com.company.users;

public class Customer extends User{
    private String password; // this will be hashed 
    private LoyaltyPoints loyaltyPoints;

    public Customer(){
        // code
    }

    public Customer(int userId, String email, String fullName, String password, int points) {
        super.setIdNum(userId);
        super.setEmail(email);
        super.setFullName(fullName);
        this.password = password;
        loyaltyPoints = new LoyaltyPoints(points);
    }

    protected void addLoyaltyProgramme(int userId) {

    }

    protected void leaveFeedback(int orderId, String feedback){
        // code
    }

    protected int getLoyaltyPoints(){
        return this.loyaltyPoints.getLoyaltyPoints();
    }
}


