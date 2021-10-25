package src.com.company.users;

abstract class User {
    private int idNum;
    private String userType;
    private String email;
    
    public void logon(String username, String password);
    public void logout();
    public void placeOrder(int userID, Order order);
    public void cancelOrder(int orderID);
    public void viewMenu(int menu_id);
    public void payOrder(int order_id);
    public void addLoyaltyProgramme(int userID);
}
