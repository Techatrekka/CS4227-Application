package src.com.company.users;

public class Customer implements user{
    private int id_num;
    private String full_name;
    private String email;

    public Customer(int id_num, String full_name, String email) {
        this.id_num = id_num;
        this.full_name = full_name;
        this.email = email;
    }

    @Override
    public void logout() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void logon(String username, String password) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void viewMenu(int menu_id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void placeOrder(int order_id, Order order) {
        // TODO Auto-generated method stub
    }

    @Override
    public void cancelOrder(int order_id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void payOrder(int order_id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addLoyaltyProgramme(int id_num) {
        // TODO Auto-generated method stub
        
    }

    public void leaveFeedback(int order_id, String feedback){
        // code
    }
}


