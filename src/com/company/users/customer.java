package src.com.company.users;

public class Customer extends User{
    private String full_name;
    private String password; // this will be hashed 
    private LoyaltyPoints loyaltyPoints;

    public Customer(){
        // code
    }

    public Customer(String full_name, String password) {
        this.full_name = full_name;
        this.password = password;
        this.loyaltyPoints = new LoyaltyPoints();
    }

    protected void leaveFeedback(int order_id, String feedback){
        // code
    }

    protected int getLoyaltyPoints(){
        return this.loyaltyPoints.getLoyaltyPoints();
    }
}


