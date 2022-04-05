package users;

import restaurant.Observable;

public class Customer extends User implements Observer{
    private LoyaltyPoints loyaltyPoints;

    public Customer(){
    }

    public Customer(int userId, String email, String fullName, int points) {
        super.setIdNum(userId);
        super.setEmail(email);
        super.setFullName(fullName);
        super.setUserType("customer");
        loyaltyPoints = new LoyaltyPoints(points);
    }

    public static void addObservable(Customer customer, Observable observable) {
        observable.registerObserver(customer);
    }

    protected void addLoyaltyProgramme(int userId) {

    }

    protected void leaveFeedback(int orderId, String feedback){
        // code
    }

    public int getLoyaltyPoints(int userId){
        return this.loyaltyPoints.getLoyaltyPoints(userId);
    }

    public String toString() {
        return "User id: " + super.getIdNum() + "\nCustomer Name: " + super.getFullName() + "\nLoyalty Points: " + getLoyaltyPoints(getIdNum());
    }

    @Override
    public void update(String updates) {
        System.out.println(updates);
    }
}
