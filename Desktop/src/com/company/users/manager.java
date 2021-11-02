package src.com.company.users;
import java.util.Date;

public class manager implements staff {
    private int id_num;
    private String full_name;
    private String email;
    private String user_type;

    @Override
    public void logon(String username, String password) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void viewMenu(int menu_id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void Customer(int id_num, String full_name, String email) {
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

    protected void addStaff(int id_num, String full_name, String email, String user_type) {
        this.id_num = id_num;
        this.full_name = full_name;
        this.email = email;
        this.user_type = user_type;
    }

    public void removeStaff(int id_num){
        // code
    }

    public void editStaff(int id_num, String full_name, String email){
        // code
    }

    public void makeMenu(){
        // code
    }

    public void editMenu(int menu_id, String edits){
        // code
    }

    public void removeMenu(int menu_id){
        // code
    }

    public void addMenuItem(int menu_id, int item_id, String item){
        // code
    }

    public void editMenuItem(int item_id){
        // code
    }

    public void removeMenuItem(int item_id, int menu_id){
        // code
    }

    public void payStaff(){
        // code
    }
}
