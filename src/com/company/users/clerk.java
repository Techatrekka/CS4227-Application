package src.com.company.users;

import java.util.Date;

public class Clerk implements staff{
    private int id_num;
    private String full_name;
    private String email;
    private String user_type;

    @Override
    public void logon(String username, String password) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void logout() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void viewMenu(int menu_id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void orderStock(Date date, String order) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void takeStock(Date date, String stock, int id_num) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void takePayment() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void placeOrder(int order_id, String order) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void editOrder(int order_id, String new_order) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void cancelOrder(int order_id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void redeemVoucher(int order_id, int voucher_number) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void redeemCoupon(int order_id, int coupon_number) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addLoyaltyProgramme(int id_num) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void payOrder(int order_id) {
        // TODO Auto-generated method stub
        
    }
}
